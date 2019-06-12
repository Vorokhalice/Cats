package com.example.cats;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CatsService {
    @GET("/v1/images/search")
    Call<List<Images>> getVoteImage(@Header("x-api-key") String appID, @Query("mime_types") String types);
    @POST("/v1/votes")
    Call<ResponseBody> postVote(@Body MyVote myVote, @Header("x-api-key") String appID, @Header("content-type") String type);
    @POST("/v1/favourites")
    Call<ResponseBody> postFave(@Header("x-api-key") String appID, @Header("content-type") String type, @Body MyFave myFave);
    @GET("/v1/favourites")
    Call<List<Faves>> getFaves(@Header("x-api-key") String appID, @Query("sub_id") String sub_id);
    @DELETE("/v1/favourites/{favourite_id}")
    Call<ResponseBody> deleteFave(@Path("favourite_id")String id, @Header("x-api-key") String appID);
    @GET("/v1/breeds")
    Call<List<Breeds>> getBreeds(@Header("x-api-key") String appID);
    @GET("/v1/images/search")
    Call<List<Images>> getBreed(@Query("breed_id")String name, @Query("mime_types") String types, @Header("x-api-key") String appID);
    @GET("/v1/categories")
    Call<List<Categories>> getCategories(@Header("x-api-key") String appID);
    @GET("/v1/images/search")
    Call<List<Images>> getImages(@Query("limit") int limit, @Query("mime_types") String types, @Query("order") String ord, @Query("category_ids") int category , @Header("x-api-key") String appID);
    @GET("/v1/images/search")
    Call<List<Images>> getAllImages(@Query("limit") int limit, @Query("mime_types") String types, @Query("order") String ord, @Header("x-api-key") String appID);
}
