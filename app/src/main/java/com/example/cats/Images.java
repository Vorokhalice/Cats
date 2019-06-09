package com.example.cats;

import java.util.List;

public class Images
{
    private List<Breeds> breeds;

    private String id;

    private String url;

    private int width;

    private int height;

    public void setBreeds(List<Breeds> breeds){
            this.breeds = breeds;
    }
    public List<Breeds> getBreeds(){
            return this.breeds;
    }
    public void setId(String id){
            this.id = id;
    }
    public String getId(){
            return this.id;
    }
    public void setUrl(String url){
            this.url = url;
    }
    public String getUrl(){
            return this.url;
    }
    public void setWidth(int width){
            this.width = width;
    }
    public int getWidth(){
            return this.width;
    }
    public void setHeight(int height){
            this.height = height;
    }
    public int getHeight(){
            return this.height;
    }
}