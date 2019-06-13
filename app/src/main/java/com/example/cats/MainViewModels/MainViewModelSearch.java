package com.example.cats.mainViewModels;

import com.example.cats.data.Repository;
import com.example.cats.entities.CategoriesEntity;
import com.example.cats.entities.CategoryEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModelSearch extends ViewModel {
    private Repository repository;
    LiveData<List<CategoriesEntity>> categoriesData;
    LiveData<List<CategoryEntity>> categoryData;
    private int id = 0;
    public void loadCategoriesData(Repository repository) {
        if (categoriesData == null) {
            this.repository = repository;
            categoriesData = repository.getCategoriesData();
        }
    }
    public LiveData<List<CategoriesEntity>> getCategoriesData(){
        return categoriesData;
    }
    public void loadCategoryData(Repository repository, int id) {
        if (this.id != id) {
            this.repository = repository;
            categoryData = repository.getCategoryData(id);
            this.id = id;
        }
    }
    public LiveData<List<CategoryEntity>> getCategoryData(){
        return categoryData;
    }
    public void loadNonCategoryData(Repository repository, int id) {
        if (this.id != id) {
            this.repository = repository;
            categoryData = repository.getNonCategoryData();
            this.id = id;
        }
    }
    public LiveData<List<CategoryEntity>> getNonCategoryData(){
        return categoryData;
    }
}
