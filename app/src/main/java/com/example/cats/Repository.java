package com.example.cats;

import android.content.Context;
import android.util.Log;

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
                List<Faves> faves = remoteDataSource.getMyFaves();
                localDataSource.storeFaves(faves);
            }

        });
    }
    public void postFaveData(final MyFave myFave) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                remoteDataSource.postMyFave(myFave);
                List<Faves> faves = remoteDataSource.getMyFaves();
                Log.e("REPOSITORY", ""+faves);
                localDataSource.storeFaves(faves);
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

    public LiveData<List<CategoriesEntity>> getCategoriesData() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<Categories> categories = remoteDataSource.getCategories();
                localDataSource.storeCategories(categories);
            }

        });
        return localDataSource.getCategories();
    }

    public LiveData<List<CategoryEntity>> getCategoryData(final int id) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<Images> category= remoteDataSource.getImages(id);
                localDataSource.storeCategory(category);
            }

        });
        return localDataSource.getCategory();
    }

    public LiveData<List<CategoryEntity>> getNonCategoryData() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<Images> category= remoteDataSource.getAllImages();
                localDataSource.storeCategory(category);
            }

        });
        return localDataSource.getCategory();
    }
}
