package com.example.cats.pojo;

import java.io.File;

public class ImageUpload {
    private File file;
    private String sub_id;
    public void setFile(File file){
        this.file = file;
    }
    public File getFile(){
        return this.file;
    }
    public void setSub_id(String sub_id){
        this.sub_id = sub_id;
    }
    public String getSub_id(){
        return this.sub_id;
    }
}
