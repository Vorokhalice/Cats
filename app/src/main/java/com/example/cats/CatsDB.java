package com.example.cats;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {VoteEntity.class, FaveEntity.class, BreedsEntity.class, BreedEntity.class}, version = 1, exportSchema = false)
public abstract class CatsDB extends RoomDatabase {
    public abstract CatsDao catsDao();
}
