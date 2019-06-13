package com.example.cats.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category_table")
public class CategoryEntity {
    @PrimaryKey
    public Integer key;
    public String imageCategoryUrl;
    public String imageCategoryId;
}
