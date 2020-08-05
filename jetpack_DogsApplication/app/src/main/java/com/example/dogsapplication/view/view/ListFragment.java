package com.example.dogsapplication.view.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dogsapplication.R;
import com.example.dogsapplication.view.model.DogBreed;
import com.example.dogsapplication.view.viewmodel.ListViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListFragment extends Fragment {

    /* 1. 데이터 접근을 위한 viewmodel과 list 표현을 위한 adapter 생성
    * 2. 리스트 영역에 표현되는 view들 xml file 로부터 binding
    * 3. default 생성자 정의
    * 4. onCreateView : fragment 가 생성될때, 필요한 내용 정의
    *   - inflator 를 이용해 xml resource 에서 요소들 로딩
    * 5. onViewCreated : fragment view 가 생성되자마자 필요한 내용 정의  */

    private ListViewModel listViewModel;
    private DogsListAdapter dogsListAdapter = new DogsListAdapter(new ArrayList<>());

    @BindView(R.id.dogsList)
    RecyclerView dogsList;

    @BindView(R.id.listErrorMsg)
    TextView listErrorMsg;

    @BindView(R.id.loadingView)
    ProgressBar loadingView;

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    public ListFragment() {
    }

    /* Fragment 가 더해지면, onAttach() -> onCreate() -> 바로 onCreateView() 실행되고 -> onViewCreated() 호출됨  */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false); //리스트 xml file 에 있는 resource 들을 로딩하여
        ButterKnife.bind(this, view);                                                   //view 생성 시에 같이 bind 해준다.
        setHasOptionsMenu(true);    /* Declare that the options menu has changed, so should be recreated. */
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //detail fragment 로 바로 연결하여 페이지 내용 확인!
        //ListFragmentDirections.ActionDetail action = ListFragmentDirections.actionDetail();
        //Navigation.findNavController(view).navigate(action);

        listViewModel = ViewModelProviders.of(this).get(ListViewModel.class);   //list viewmodel 을 정의하고 그 내부에 있는 refresh() 함수를 실행시켜준다.
        /* of
            @param fragment a fragment, in whose scope ViewModels should be retained
         *  @return a ViewModelProvider instance
         * get
         * getCanonicalName : 기본형, 원형 */
        listViewModel.refresh();    //list fragment view 에서는 refresh() 함수의 내용을 알지 못하고, 어떤 정보들이 들어있는지 모른다. --> 의존성 없앰

        /* RecyclerView 사용하기위한 밑작업 : 레이아웃 매니저, 어뎁터 세팅  */
        dogsList.setLayoutManager(new LinearLayoutManager(getContext()));
        dogsList.setAdapter(dogsListAdapter);

        /* refresh listener */
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {   //swipe refresh 동작을 하는 순간을 정의
                loadingView.setVisibility(View.VISIBLE);
                dogsList.setVisibility(View.GONE);
                listErrorMsg.setVisibility(View.GONE);
                listViewModel.refreshBypassCache();
                refreshLayout.setRefreshing(false);
            }
        });

        //livedata 를 observe 하는 method 정의
        observeViewModel();
    }

    private void observeViewModel() {
        listViewModel.dogs.observe(this, new Observer<List<DogBreed>>() {   //lifeycler owner : ListFragment + new Observer (List<DogBreed> 타입) 객체 생성
            @Override
            public void onChanged(List<DogBreed> dogBreeds) {               //LiveData 에 변화가 감지될 때마다, ui 변경
                if (dogBreeds != null && dogBreeds instanceof List) {
                    dogsList.setVisibility(View.VISIBLE);
                    dogsListAdapter.updateDogsList(dogBreeds);
                }
            }
        });

        listViewModel.dogLoadError.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if (isError != null && isError instanceof Boolean) {
                    listErrorMsg.setVisibility(isError ? View.VISIBLE : View.GONE);
                }
            }
        });

        listViewModel.loading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading != null && isLoading instanceof Boolean) {
                    loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                    if (isLoading) {
                        listErrorMsg.setVisibility(View.GONE);
                        dogsList.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    /* settings menu */
    /* onCreateOptionsMenu : menu inflater 를 통해 menu xml file 을 menu 에 연결 */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.list_menu, menu);
    }

    /* 옵션을 열면 선택되는 케이스들마다 미리 정의된 direction 으로 이동함 */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionSettings : {
                if(isAdded()) {
                    Navigation.findNavController(getView()).navigate(ListFragmentDirections.actionSettings());
                    /*
                    Navigation.findNavController(fragment).nagivate(direction)
                    //fragment 로부터 이동하고자하는 direction 을 매개변수로 전달해줌
                    s you navigate through your app, you tell the NavController that you want to navigate either
                    along a specific path in your navigation graph or directly to a specific destination.
                    The NavController then shows the appropriate destination in the NavHost.
                     */
                }
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

