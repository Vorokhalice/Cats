package com.example.cats.mainViewModels;

import com.example.cats.data.Repository;
import com.example.cats.entities.VoteEntity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModelVote extends ViewModel {
    public MainViewModelVote(){}
    private Repository repository;
    LiveData<VoteEntity> voteData;
    public void loadVoteData(Repository repository) {
        this.repository = repository;
        voteData = repository.getVoteData();
    }
    public LiveData<VoteEntity> getVoteData(){
        return voteData;
    }
}
