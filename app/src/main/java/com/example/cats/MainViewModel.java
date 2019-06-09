package com.example.cats;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    public MainViewModel(){}
    private Repository repository;
    LiveData<VoteEntity> voteData;
    LiveData<List<FaveEntity>> faveData;
    LiveData<List<BreedsEntity>> breedsData;
    LiveData<BreedEntity> breedData;
    public void loadVoteData(Repository repository) {
        if (voteData == null) {
            this.repository = repository;
            voteData = repository.getVoteData();
        }
    }
    public LiveData<VoteEntity> getVoteData(){
        return voteData;
    }
    public void loadFaveData(Repository repository) {
        if (faveData == null) {
            this.repository = repository;
            faveData = repository.getFaveData();
        }
    }
    public LiveData<List<FaveEntity>> getFaveData(){
        return faveData;
    }
    public void loadBreedsData(Repository repository) {
        if (breedsData == null) {
            this.repository = repository;
            breedsData = repository.getBreedsData();
        }
    }
    public LiveData<List<BreedsEntity>> getBreedsData(){
        return breedsData;
    }
    public void loadBreedData(Repository repository, String id) {
        if (breedData == null) {
            this.repository = repository;
            breedData = repository.getBreedData(id);
        }
    }
    public LiveData<BreedEntity> getBreedData(){
        return breedData;
    }
}
