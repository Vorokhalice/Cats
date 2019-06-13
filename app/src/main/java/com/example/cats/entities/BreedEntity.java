package com.example.cats.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "breed_table")
public class BreedEntity {
    @PrimaryKey
    public Integer key;
    public String imageBreedUrl;
}
