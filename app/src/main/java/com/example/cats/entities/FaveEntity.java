package com.example.cats.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fave_table")
public class FaveEntity {
    @PrimaryKey
    public Integer key;
    public String faveImageUrl;
    public String fave_id;
}
