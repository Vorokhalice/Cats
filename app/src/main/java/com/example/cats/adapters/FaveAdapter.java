package com.example.cats.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.cats.fragments.Fave;
import com.example.cats.R;
import com.example.cats.data.Repository;
import com.example.cats.entities.FaveEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FaveAdapter  extends RecyclerView.Adapter<FaveAdapter.FaveHolder>{
    private List<FaveEntity> faves;
    private Repository repository;
    public FaveAdapter(List<FaveEntity> faves, Repository repository) {
        this.faves = faves;
        this.repository = repository;
    }
    public void changeData(List<FaveEntity> faves) {
        this.faves = faves;
        notifyDataSetChanged();
    }
    class FaveHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Button faveDelete;
        private ImageView faveImage;
        private SharedPreferences sharedPreferences;
        private SharedPreferences.Editor editor;

        public FaveHolder(View view) {
            super(view);
            faveImage = view.findViewById(R.id.fave_image);
            faveDelete = view.findViewById(R.id.fave_delete);
            faveDelete.setOnClickListener(this);
            sharedPreferences = Fave.getContextOfFave().getSharedPreferences("Prefs", Context.MODE_PRIVATE);
        }

       @Override
        public void onClick(View v) {
            repository.deleteFaveData(faves.get(getAdapterPosition()).fave_id);
        }

        public void setContent(FaveEntity faves) {
            Picasso.get().load(faves.faveImageUrl).into(faveImage);
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
