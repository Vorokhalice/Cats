package com.example.cats;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModelFave extends ViewModel {
    public MainViewModelFave(){}
    private Repository repository;
    LiveData<List<FaveEntity>> faveData;
    public void loadFaveData(Repository repository) {
        //if (faveData == null) {
            this.repository = repository;
            faveData = repository.getFaveData();
        //}
    }
    public LiveData<List<FaveEntity>> getFaveData(){
        return faveData;
    }
}
