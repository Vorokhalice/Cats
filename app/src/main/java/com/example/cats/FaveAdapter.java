package com.example.cats;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Path;

import static android.view.View.GONE;

public class FaveAdapter  extends RecyclerView.Adapter<FaveAdapter.FaveHolder>{
    private List<FaveEntity> faves;
    public FaveAdapter(List<FaveEntity> faves) {
        this.faves = faves;
    }
    public void changeData(List<FaveEntity> faves) {
        this.faves = faves;
        notifyDataSetChanged();
    }
    class FaveHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Button faveDelete;
        private ImageView faveImage;

        public FaveHolder(View view) {
            super(view);
            faveImage = view.findViewById(R.id.fave_image);
            faveDelete = view.findViewById(R.id.fave_delete);
            faveDelete.setOnClickListener(this);
        }

       @Override
        public void onClick(View v) {
            Repository repository = new Repository(Fave.getContextOfFave());
            repository.deleteFaveData(faves.get(getAdapterPosition()).fave_id);
        }

        public void setContent(FaveEntity faves) {
            Picasso.get().load(faves.faveImageUrl).into(faveImage);
            Log.e("adapter","Exception");
        }
    }
    @NonNull
    @Override
    public FaveHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fave_item, viewGroup, false);
        return new FaveHolder(view);
    }

    @Override
    public void onBindViewHolder(FaveHolder faveHolder, int i) {
        faveHolder.setContent(faves.get(i));
    }

    @Override
    public int getItemCount() {
        return faves.size();
    }
}
