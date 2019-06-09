package com.example.cats;

public class Faves {
        private String id;

        private String user_id;

        private String image_id;

        private String sub_id;

        private String created_at;

        private Image image;

        public void setId(String id){
        this.id = id;
    }
        public String getId(){
        return this.id;
    }
        public void setUser_id(String user_id){
        this.user_id = user_id;
    }
        public String getUser_id(){
        return this.user_id;
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
        public void setCreated_at(String created_at){
        this.created_at = created_at;
    }
        public String getCreated_at(){
        return this.created_at;
    }
        public void setImage(Image image){
        this.image = image;
    }
        public Image getImage(){
        return this.image;
    }
}
