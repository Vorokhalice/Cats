package com.example.cats;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;

public class Repository {
    private LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;
    public Repository(Context context) {
        localDataSource = new LocalDataSource(context);
        remoteDataSource = new RemoteDataSource();
    }
    public LiveData<VoteEntity> getVoteData() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<Images> images = remoteDataSource.getImagesForVote();
                localDataSource.storeImageForVote(images);
            }

        });
        return localDataSource.getImageForVote();
    }
    public LiveData<List<FaveEntity>> getFaveData() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<Faves> faves = remoteDataSource.getMyFaves();
                localDataSource.storeFaves(faves);
            }

        });
        return localDataSource.getFaveImages();
    }
    public void deleteFaveData(final String fav_id){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                remoteDataSource.deleteMyFave(fav_id);
            }

        });
    }
    public void postFaveData(final MyFave myFave) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                remoteDataSource.postMyFave(myFave);
            }

        });
    }
    public void postVoteData(final MyVote myVote) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                remoteDataSource.postMyVote(myVote);
            }

        });
    }
    public LiveData<List<BreedsEntity>> getBreedsData() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<Breeds> breeds = remoteDataSource.getBreeds();
                localDataSource.storeBreeds(breeds);
            }

        });
        return localDataSource.getBreeds();
    }

    public LiveData<BreedEntity> getBreedData(final String id) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<Images> breed = remoteDataSource.getBreed(id);
                localDataSource.storeBreed(breed);
            }

        });
        return localDataSource.getBreed();
    }
}
