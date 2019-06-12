package com.example.cats;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CatsDao {
    @Insert
    public void insertVote(VoteEntity voteEntity);
    @Query("SELECT * FROM vote_table")
    public LiveData<VoteEntity> getVote();
    @Query("DELETE FROM vote_table")
    public void deleteVote();
    @Insert
    public void insertFave(List<FaveEntity> faveEntity);
    @Query("SELECT * FROM fave_table")
    public LiveData<List<FaveEntity>> getFave();
    @Query("DELETE FROM fave_table")
    public void deleteFave();
    @Update
    public void updateFave(List<FaveEntity> faveEntity);
    @Insert
    public void insertBreeds(List<BreedsEntity> breedsEntity);
    @Query("SELECT * FROM breeds_table")
    public LiveData<List<BreedsEntity>> getBreeds();
    @Query("DELETE FROM breeds_table")
    public void deleteBreeds();
    @Insert
    public void insertBreed(BreedEntity breedEntity);
    @Query("SELECT * FROM breed_table")
    public LiveData<BreedEntity> getBreed();
    @Query("DELETE FROM breed_table")
    public void deleteBreed();
    @Insert
    public void insertCategories(List<CategoriesEntity> categoriesEntity);
    @Query("SELECT * FROM categories_table")
    public LiveData<List<CategoriesEntity>> getCategories();
    @Query("DELETE FROM categories_table")
    public void deleteCategories();
    @Insert
    public void insertCategory(List<CategoryEntity> categoriesEntity);
    @Query("SELECT * FROM category_table")
    public LiveData<List<CategoryEntity>> getCategory();
    @Query("DELETE FROM category_table")
    public void deleteCategory();
}
