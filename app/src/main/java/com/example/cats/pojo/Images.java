package com.example.cats.pojo;

import java.util.List;

public class Images
{
    private List<Breeds> breeds;

    private String id;

    private String url;

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
}