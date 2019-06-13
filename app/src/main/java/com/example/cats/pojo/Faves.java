package com.example.cats.pojo;

public class Faves {
        private String id;

        private String image_id;

        private String sub_id;

        private Image image;

        public void setId(String id){
        this.id = id;
    }
        public String getId(){
        return this.id;
    }
        public void setImage_id(String image_id){
        this.image_id = image_id;
    }
        public String getImage_id(){
        return this.image_id;
    }
        public void setSub_id(String sub_id){
        this.sub_id = sub_id;
    }
        public String getSub_id(){
        return this.sub_id;
    }
        public void setImage(Image image){
        this.image = image;
    }
        public Image getImage(){
        return this.image;
    }
}
