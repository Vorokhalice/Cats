package com.example.cats;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModelVote extends ViewModel {
    public MainViewModelVote(){}
    private Repository repository;
    LiveData<VoteEntity> voteData;
    public void loadVoteData(Repository repository) {
        //if (voteData == null) {
        this.repository = repository;
        voteData = repository.getVoteData();
        //}
    }
    public LiveData<VoteEntity> getVoteData(){
        return voteData;
    }
}
