package com.example.cats;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import androidx.lifecycle.LiveData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class RemoteDataSource {
    private static String API_KEY = "19da309d-127a-4147-8bb8-4c0c05e39452";
    private CatsService catsService;
    private SharedPreferences preferences;

    public RemoteDataSource(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thecatapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        catsService = retrofit.create(CatsService.class);
    }


    public List<Images> getImagesForVote() {
        Call<List<Images>> call = catsService.getVoteImage(API_KEY, "jpg,png");
        try {
            Response<List<Images>> response = call.execute();
            if (response.isSuccessful()) {
                Log.e("Remote", " "+ response.body());
                return response.body();
            }
        } catch (IOException ioex) {
            Log.e("Remote", "Images for vote " + ioex);
        }
        return null;
    }

    public List<Faves> getMyFaves() {
        preferences = MainActivity.getContextOfApplication().getSharedPreferences("MyPrefs", MODE_PRIVATE);
            Call<List<Faves>> call = catsService.getFaves(API_KEY, preferences.getString("name", null));
            try {
                Response<List<Faves>> response = call.execute();
                if (response.isSuccessful()) {
                    Log.e("Remote", "Faves OK");
                    return response.body();
                } else {
                    return null;
                }
            } catch (IOException e) {
                Log.e("Remote", "ERROR" + e);
                return null;
        }
    }

    public void deleteMyFave(String favourite_id) {
        Call<ResponseBody> call = catsService.deleteFave(favourite_id, API_KEY);
        try {
            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()) {
                Log.e("Remote ", "Deleted");
            }
            else {BufferedReader reader = null;
            StringBuilder sb = new StringBuilder();
            try {
                reader = new BufferedReader(new InputStreamReader(response.errorBody().byteStream()));
                String line;
                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            String finallyError = sb.toString();
            Log.e("Remote", "delete "+finallyError);
        }
        }catch (IOException e) {
            Log.e("Remote", "Error " + e);
        }
    }

    public void postMyFave(MyFave myFave) {
        Call<ResponseBody> call = catsService.postFave(API_KEY, "application/json", myFave);
        try {
            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()) {
                Log.e("REMOTE", "FAVED");
            }
            else {BufferedReader reader = null;
                StringBuilder sb = new StringBuilder();
                try {
                    reader = new BufferedReader(new InputStreamReader(response.errorBody().byteStream()));
                    String line;
                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String finallyError = sb.toString();
                Log.e("Remote", "post fave  "+finallyError);
            }
        }catch (IOException e) {
            Log.e("Remote", "Error " + e);
        }
    }
    public void postMyVote(MyVote myVote) {
        Call<ResponseBody> call = catsService.postVote(myVote, API_KEY, "application/json");
        try {
            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()) {
                Log.e("Remote", "voted");
            }
            else {BufferedReader reader = null;
                StringBuilder sb = new StringBuilder();
                try {
                    reader = new BufferedReader(new InputStreamReader(response.errorBody().byteStream()));
                    String line;
                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String finallyError = sb.toString();
                Log.e("Remote", "post vote  "+finallyError);
            }
        }catch (IOException e) {
            Log.e("Remote", "Error " + e);
        }
    }
    public List<Breeds> getBreeds() {
        Call<List<Breeds>> call = catsService.getBreeds(API_KEY);
        try {
            Response<List<Breeds>> response = call.execute();
            if (response.isSuccessful()) {
                Log.e("Remote", " "+ response.body());
                return response.body();
            }
        } catch (IOException ioex) {
            Log.e("Remote", "Breeds " + ioex);
        }
        return null;
    }
    public List<Images> getBreed(String breed_id) {
        Call<List<Images>> call = catsService.getBreed(breed_id, "jpg,png", API_KEY);
        try {
            Response<List<Images>> response = call.execute();
            if (response.isSuccessful()) {
                Log.e("Remote breed", " "+ response.body());
                return response.body();
            }
        } catch (IOException ioex) {
            Log.e("Remote", "Breed " + ioex);
        }
        return null;
    }
    public List<Categories> getCategories() {
        Log.e("RemoteSearch","");
        Call<List<Categories>> call = catsService.getCategories(API_KEY);
        try {
            Response<List<Categories>> response = call.execute();
            if (response.isSuccessful()) {
                Log.e("Remote", "categories ok");
                return response.body();
            }
        } catch (IOException ioex) {
            Log.e("Remote", "categories " + ioex);
        }
        return null;
    }
    public List<Images> getImages(int category) {
        Call<List<Images>> call = catsService.getImages(20, "jpg,png","random", category, API_KEY);
        try {
            Response<List<Images>> response = call.execute();
            if (response.isSuccessful()) {
                Log.e("Remote", "category images ok");
                return response.body();
            }
        } catch (IOException ioex) {
            Log.e("Remote", "category images " + ioex);
        }
        return null;
    }
    public List<Images> getAllImages() {
        Call<List<Images>> call = catsService.getAllImages(20, "jpg,png", "random", API_KEY);
        try {
            Response<List<Images>> response = call.execute();
            if (response.isSuccessful()) {
                Log.e("Remote", "images ok");
                return response.body();
            }
        } catch (IOException ioex) {
            Log.e("Remote", "images " + ioex);
        }
        return null;
    }
}
