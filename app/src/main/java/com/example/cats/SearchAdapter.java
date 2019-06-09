package com.example.cats;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.GONE;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder>{
    private List<Images> images;
    public SearchAdapter(List<Images> images) {
        this.images = images;
    }
    public void changeData(List<Images> images) {
        this.images = images;
        notifyDataSetChanged();
    }
    class SearchHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView searchImage;
        private Button searchFave;
        private MyFave myFave;
        private SharedPreferences sharedPreferences;

        public SearchHolder(View view) {
            super(view);
            searchImage = view.findViewById(R.id.search_image);
            searchFave = view.findViewById(R.id.search_fave);
            searchFave.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myFave = new MyFave();
            sharedPreferences = MainActivity.getContextOfApplication().getSharedPreferences("MyPrefs", MODE_PRIVATE);
            myFave.setImage_id(images.get(getAdapterPosition()).getId());
            myFave.setSub_id(sharedPreferences.getString("name", null));
            Log.e("Search", sharedPreferences.getString("name", null));
            Repository repository = new Repository(Search.getContextOfSearch());
            repository.postFaveData(myFave);
        }

        public void setContent(Images image) {
            Picasso.get().load(image.getUrl()).into(searchImage);
        }
    }
    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_item, viewGroup, false);
        return new SearchHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchHolder searchHolder, int i) {
        searchHolder.setContent(images.get(i));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

}
