package com.example.dogsapplication.view.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.dogsapplication.view.model.DogBreed;
import com.example.dogsapplication.view.model.DogDao;
import com.example.dogsapplication.view.model.DogDatabase;
import com.example.dogsapplication.view.model.DogsApiService;
import com.example.dogsapplication.view.util.NotificationsHelper;
import com.example.dogsapplication.view.util.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends AndroidViewModel {

    /* why use ViewModel instead of AndroidViewModel ?
     * - 뷰 모델을 안드로이드 뷰모델과 완전히 분리하기 위해서
     * - life cycle 이 짧다.
     *
     * then why use AndroidViewModel instead of ViewModel
     * - 컨텍스트에 접근하기 위해서는 AndroidViewModel 을 사용해야함
     * - viewmodel 에 비해 life cycle 이 더 길다 */

    /* viewmodel 은 하나의 view 에 응답한다
     * - 이 경우는 listFragment 에 응답하는 listviewmodel 임 */

    public MutableLiveData<List<DogBreed>> dogs = new MutableLiveData<List<DogBreed>>();
    public MutableLiveData<Boolean> dogLoadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    private DogsApiService dogsApiService = new DogsApiService();   //retrofit 객체 생성
    private CompositeDisposable disposable = new CompositeDisposable(); //메모리 관리를 위한 disposable 객체 생성

    //db 작업 수행을 위한 비동기 task
    private AsyncTask<List<DogBreed>, Void, List<DogBreed>> insertDogTask;
    private AsyncTask<Void, Void, List<DogBreed>> retrieveDogTask;

    private SharedPreferencesHelper preferencesHelper = SharedPreferencesHelper.getInstance(getApplication());
    private long refreshTime = 5 * 60 * 1000 * 1000 * 1000L;    //5분

    public ListViewModel(@NonNull Application application) {
        super(application);
    }

    public void refresh() {
        checkCacheDuration();

        long updateTime = preferencesHelper.getUpdateTime();    //updatetime
        long currentTime = System.nanoTime();   //현재시각

        if(updateTime != 0 && currentTime - updateTime < refreshTime) {     //업데이트 된지 5분 이내이면, 로컬 DB 에서 데이터 가져오고, 그게 아니면 다시 server api 로부터 받아오도록 처리
            fetchFromDatabase();
        } else {
            fetchFromRemote();
        }
    }

    private void checkCacheDuration() {
        String cachePreference = preferencesHelper.getCacheDuration();

        if(!cachePreference.equals("")) {
            try {
                int cachePreferenceInt = Integer.parseInt(cachePreference);
                refreshTime = cachePreferenceInt * 1000 * 1000 * 1000L;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

    }

    private void fetchFromDatabase() {
        loading.setValue(true); //로딩 표시
        retrieveDogTask = new RetrieveDogTask();
        retrieveDogTask.execute();
    }

    private void fetchFromRemote() {
        //최초 로딩시
        loading.setValue(true);
        disposable.add(
                dogsApiService.getDogs()    //onViewCreate : retrofit 통신 통해서 리스트 받아오기 + ui 에 표현
                        .subscribeOn(Schedulers.newThread())            //옵저버가 구독 + 통신의 결과가 얼마나 빨리 넘어올지 모르기 때문에, 백그라운드 스레드에서 getDogs() 통신이 진행되도록 한다.
                        .observeOn(AndroidSchedulers.mainThread())      //옵저버가 구독한 결과나오면, observe 하고 있다가 다시 android의 main thread 에 포함시켜준다.
                        .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {     //Single 이 observable 하기 때문에 subscribeWith
                            @Override
                            //한번만 쓰고 버리는 (disposable) Single Observer를 사용하여 메모리누수 등을 예방함
                            public void onSuccess(List<DogBreed> dogBreeds) {
                                //통신 성공시,
                                //1) 로컬 db에 저장한 후, - 따로 AsyncTask 를 이용하여 비동기 방식으로 처리 (안드로이드가 foreground thread 에서 실행하지 못하도록 함.
                                // 따라서 DB 관련 task 들은 항상 async 방식으로 처리하도록 함.
                                //2) db에서 조회된 데이터로 ui 그리기
                                insertDogTask = new InsertDogTask();
                                insertDogTask.execute(dogBreeds);
                                Toast.makeText(getApplication(), "서버api로부터 개 리스트 불러옵니다", Toast.LENGTH_SHORT).show();
                                NotificationsHelper.getInstance(getApplication()).createNotification();
                            }

                            @Override
                            public void onError(Throwable e) {
                                //통신 에러시 표시할 ui 설정 : LiveData 에 setValue() 해주면, 데이터 내용이 바뀌면서 ui 가 바뀌어 표시됨
                                dogLoadError.setValue(true);        //에러 발생시 미리 설정해둔 error msg 표시되도록 true
                                loading.setValue(false);            //로딩 버튼은 숨기기, 리스트는 에러로 내역표시 안될것임
                                e.printStackTrace();
                            }
                        })
        );
    }

    //로컬 db 로부터 조회된 데이터를 전달받아 ui 로 그리는 과정
    private void dogsRetrieved(List<DogBreed> list) {
        dogs.setValue(list);
        dogLoadError.setValue(false);
        loading.setValue(false);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear(); //이미 disposed 되었다면 ok, disposed 되지 않았다면, dispose() 통해 자원 처리

        if(insertDogTask != null) {
            insertDogTask.cancel(true);
            insertDogTask = null;
        }

        if(retrieveDogTask != null) {
            retrieveDogTask.cancel(true);
            retrieveDogTask = null;
        }
    }

    public void refreshBypassCache() {
        fetchFromRemote();  //사용자가 직접 swipe 통해서 refresh 하면 original api 에서 데이터를 받아오도록 처리함
    }

    /* insert 작업 수행할 비동기 테스크 */
    private class InsertDogTask extends AsyncTask<List<DogBreed>, Void, List<DogBreed>> {   //AsyncTask<Params, Progress, Result>
        @Override
        protected List<DogBreed> doInBackground(List<DogBreed>... lists) {
            DogDao dao = DogDatabase.getInstance(getApplication()).dogDao();
            dao.deleteAllDogs();    //룸db에 정보를 저장하기 전에, 남아있는 모든 데이터들을 우선 지운다.
                                    //db가 누적된 데이터들로 인하여 방대해지는 것을 막기 위하여

            List<DogBreed> list = lists[0];
            ArrayList<DogBreed> newList = new ArrayList<DogBreed>(list);
            List<Long> result = dao.insertAll(newList.toArray(new DogBreed[0]));

            int i = 0;
            while(i< list.size()) {
                list.get(i).setUui(result.get(i).intValue());
                ++i;
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<DogBreed> dogBreeds) {
            super.onPostExecute(dogBreeds);
            dogsRetrieved(dogBreeds);
            preferencesHelper.saveUpdateTime(System.nanoTime());
        }
    }

    /* retrieve 수행할 비동기 테스크 */
    private class RetrieveDogTask extends AsyncTask<Void, Void, List<DogBreed>> {

        /* background : db 에서 데이터 조회 결과 반환
        *  post execute : background 에서 반환된 리스트로 livedata 에 매핑 */
        @Override
        protected List<DogBreed> doInBackground(Void... voids) {
            return DogDatabase.getInstance(getApplication()).dogDao().getAllDogs();
        }

        @Override
        protected void onPostExecute(List<DogBreed> dogBreeds) {
            dogsRetrieved(dogBreeds);
            Toast.makeText(getApplication(), "로컬 DB로부터 개 리스트 불러옵니다", Toast.LENGTH_SHORT).show();
        }
    }
}
