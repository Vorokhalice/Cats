package com.example.cats;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vote_table")
public class VoteEntity {
    @PrimaryKey
    public Integer key;
    public String voteImageUrl;
    public int vote;
    public String voteImageId;
}
