package com.example.dogsapplication.view.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dogsapplication.view.model.DogBreed;
import com.example.dogsapplication.view.model.DogDatabase;

public class DetailViewModel extends AndroidViewModel {

    public MutableLiveData<DogBreed> dogLiveData = new MutableLiveData<DogBreed>();

    private RetrievedDogTask retrievedDogTask;

    public DetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void fetch(int dogId) {
        retrievedDogTask = new RetrievedDogTask();
        retrievedDogTask.execute(dogId);
    }

    private class RetrievedDogTask extends AsyncTask<Integer, Void, DogBreed> {

        @Override
        protected DogBreed doInBackground(Integer... integers) {
            int dogUui = integers[0];
            return DogDatabase.getInstance(getApplication()).dogDao().getDog(dogUui);
        }

        @Override
        protected void onPostExecute(DogBreed dogBreed) {
            dogLiveData.setValue(dogBreed);
        }
    }


}
