package com.example.cats;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "breeds_table")
public class BreedsEntity {
    @PrimaryKey
    public Integer key;
    public String breedName;
    public String breedId;
    public String temp;
    public String descr;
    public String w;
    public String lf;
    public int aff;
    public int adapt;
    public int child;
    public int dog;
    public int en;
    public int groom;
    public int intell;
    public int shed;
    public int socio;
    public int stranger;
    public int vocal;
    public String wiki;
    public int healthis;
}
