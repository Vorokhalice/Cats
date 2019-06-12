package com.example.cats;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

public class LocalDataSource {
    final CatsDB db;
    public LocalDataSource(Context context) {
        db = Room.databaseBuilder(context, CatsDB.class, "cats").build();
    }
    public void storeImageForVote(List<Images> images) {
        if (images != null) {
            db.catsDao().deleteVote();
            VoteEntity voteEntity = new VoteEntity();
            voteEntity.voteImageUrl = images.get(0).getUrl();
            voteEntity.voteImageId = images.get(0).getId();
            db.catsDao().insertVote(voteEntity);
        Log.e("Local", ""+voteEntity.voteImageUrl);
        }
    }
    public LiveData<VoteEntity> getImageForVote() {
        return db.catsDao().getVote();
    }
    public void storeFaves(List<Faves> faves) {
        if (faves != null) {
            db.catsDao().deleteFave();
            List<FaveEntity> favesList = new ArrayList<>();
            for (Faves faves1: faves) {
                FaveEntity faveEntity = new FaveEntity();
                faveEntity.faveImageUrl = faves1.getImage().getUrl();
                faveEntity.fave_id = faves1.getId();
                favesList.add(faveEntity);
            }
            db.catsDao().insertFave(favesList);
            Log.e("LOCAL", " " + favesList);
        }
    }
    public LiveData<List<FaveEntity>> getFaveImages(){
        return db.catsDao().getFave();
    }
    public void storeBreeds(List<Breeds> breeds) {
        if (breeds != null) {
            db.catsDao().deleteBreeds();
            List<BreedsEntity> breedsList = new ArrayList<>();
            for (Breeds breeds1: breeds) {
                BreedsEntity breedsEntity = new BreedsEntity();
                breedsEntity.breedName = breeds1.getName();
                breedsEntity.breedId = breeds1.getId();
                breedsEntity.temp = breeds1.getTemperament();
                breedsEntity.descr = breeds1.getDescription();
                breedsEntity.w = breeds1.getWeight().getMetric();
                breedsEntity.lf = breeds1.getLife_span();
                breedsEntity.aff = breeds1.getAffection_level();
                breedsEntity.adapt = breeds1.getAdaptability();
                breedsEntity.child = breeds1.getChild_friendly();
                breedsEntity.dog = breeds1.getDog_friendly();
                breedsEntity.en = breeds1.getEnergy_level();
                breedsEntity.groom =  breeds1.getGrooming();
                breedsEntity.healthis = breeds1.getHealth_issues();
                breedsEntity.intell =  breeds1.getIntelligence();
                breedsEntity.shed = breeds1.getShedding_level();
                breedsEntity.socio = breeds1.getSocial_needs();
                breedsEntity.stranger = breeds1.getStranger_friendly();
                breedsEntity.vocal = breeds1.getVocalisation();
                breedsEntity.wiki = breeds1.getWikipedia_url();
                breedsList.add(breedsEntity);
            }
            db.catsDao().insertBreeds(breedsList);
            Log.e("Local", "BreedsList" + breedsList);
        }
    }
    public LiveData<List<BreedsEntity>> getBreeds() {
        return db.catsDao().getBreeds();
    }
    public void storeBreed(List<Images> breed) {
        if (breed != null) {
            db.catsDao().deleteBreed();
            BreedEntity breedEntity = new BreedEntity();
            breedEntity.imageBreedUrl = breed.get(0).getUrl();
            db.catsDao().insertBreed(breedEntity);
            Log.e("Local", "Breed"+breedEntity);
        }
    }
    public LiveData<BreedEntity> getBreed() {
        return db.catsDao().getBreed();
    }
    public void storeCategories(List<Categories> categories) {
        if (categories!= null) {
            db.catsDao().deleteCategories();
            List<CategoriesEntity> categoriesList = new ArrayList<>();
            for (Categories categories1: categories) {
                CategoriesEntity categoriesEntity = new CategoriesEntity();
                categoriesEntity.category_name = categories1.getName();
                categoriesEntity.category_id = categories1.getId();
                categoriesList.add(categoriesEntity);
            }
            db.catsDao().insertCategories(categoriesList);
            Log.e("Local", "CategoriesList" + categoriesList);
        }
    }
    public LiveData<List<CategoriesEntity>> getCategories() {
        return db.catsDao().getCategories();
    }
    public void storeCategory(List<Images> images) {
        if (images!= null) {
            db.catsDao().deleteCategory();
            List<CategoryEntity> categoryList = new ArrayList<>();
            for (Images images1: images) {
                CategoryEntity categoryEntity = new CategoryEntity();
                categoryEntity.imageCategoryUrl = images1.getUrl();
                categoryEntity.imageCategoryId = images1.getId();
                categoryList.add(categoryEntity);
            }
            db.catsDao().insertCategory(categoryList);
            Log.e("Local", "BreedsList" + categoryList);
        }
    }
    public LiveData<List<CategoryEntity>> getCategory() {
        return db.catsDao().getCategory();
    }
    //public void updateFaves(List<>) { return db.catsDao().updateFave();}

}
