package com.example.cats;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories_table")
public class CategoriesEntity {
    @PrimaryKey
    public Integer key;
    public String category_name;
    public int category_id;
}
