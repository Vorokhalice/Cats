package com.example.cats.adapters;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.cats.MainActivity;
import com.example.cats.R;
import com.example.cats.data.Repository;
import com.example.cats.entities.CategoryEntity;
import com.example.cats.pojo.MyFave;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder>{
    private List<CategoryEntity> images;
    private Repository repository;
    public SearchAdapter(List<CategoryEntity> images, Repository repository) {
        this.images = images;
        this.repository = repository;
    }
    public void changeData(List<CategoryEntity> images) {
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
            sharedPreferences = MainActivity.getContextOfApplication().getSharedPreferences("Prefs", MODE_PRIVATE);
            myFave.setImage_id(images.get(getAdapterPosition()).imageCategoryId);
            myFave.setSub_id(sharedPreferences.getString("name", null));
            Log.e("Search", sharedPreferences.getString("name", null));
            repository.postFaveData(myFave);
        }

        public void setContent(CategoryEntity image) {
            Picasso.get().load(image.imageCategoryUrl).into(searchImage);
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
