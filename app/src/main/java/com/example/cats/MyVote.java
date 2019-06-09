package com.example.cats;

public class MyVote {
    private String image_id;
    private int value;
    private String sub_id;
    public void setImage_id(String image_id){
        this.image_id = image_id;
    }
    public String getImage_id(){
        return this.image_id;
    }
    public void setValue(int value){
        this.value = value;
    }
    public int getValue(){
        return this.value;
    }
    public void setSub_id(String sub_id){ this.sub_id = sub_id; }
    public String getSub_id() { return  this.sub_id; }
}
