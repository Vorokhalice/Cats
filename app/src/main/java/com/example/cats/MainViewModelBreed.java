package com.example.cats;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModelBreed extends ViewModel {
    private Repository repository;
    LiveData<List<BreedsEntity>> breedsData;
    LiveData<BreedEntity> breedData;
    String id = "";
    String url;
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
        if (!this.id.equals(id)) {
            this.repository = repository;
            breedData = repository.getBreedData(id);
            this.id = id;
        }
    }
    public LiveData<BreedEntity> getBreedData(){
        return breedData;
    }
}
