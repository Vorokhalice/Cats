package com.example.cats.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.cats.CatsApp;
import com.example.cats.mainViewModels.MainViewModelVote;
import com.example.cats.R;
import com.example.cats.data.Repository;
import com.example.cats.entities.VoteEntity;
import com.example.cats.pojo.MyFave;
import com.example.cats.pojo.MyVote;
import com.squareup.picasso.Picasso;


import static android.content.Context.MODE_PRIVATE;


public class Vote extends Fragment implements Observer<VoteEntity> {
    private MyVote myVote;
    private int vote = -1;
    SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private MainViewModelVote mainViewModelVote;
    private MyFave myFave;
    private ImageView voteImg;
    private LiveData<VoteEntity> voteData;
    private Button loveButton;
    private Button nopeButton;
    private Button faveButton;
    private Repository repository;
    private String url;
    public static Context contextOfVote;
    private String id;
    Observer<VoteEntity> observer = new Observer<VoteEntity>() {
        @Override
        public void onChanged(VoteEntity voteEntity) {
            if (voteEntity != null) {
                url = voteEntity.voteImageUrl;
                Picasso.get().load(voteEntity.voteImageUrl).into(voteImg);
                editor.putString("url", url);
                editor.putString("image_id", voteEntity.voteImageId);
                editor.apply();
            }
        }
    };

    public Vote() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onChanged(VoteEntity voteEntity) {
        if (voteEntity != null) {
            Log.e("Vote", ""+url);
            vote = sharedPreferences.getInt("vote", -1);
            url = sharedPreferences.getString("url", "");
            id = sharedPreferences.getString("image_id", "");
            if (vote == -1) {
                url = voteEntity.voteImageUrl;
                Log.e("Vote", url);
                vote = 2;
                editor.putString("url", url);
                editor.putString("image_id",voteEntity.voteImageId);
                editor.putInt("vote", vote);
                editor.apply();
            }
            Picasso.get().load(url).into(voteImg);
            myVote = new MyVote();
            loveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vote = 1;
                    myVote.setValue(vote);
                    myVote.setImage_id(id);
                    mainViewModelVote.loadVoteData(repository);
                    voteData = mainViewModelVote.getVoteData();
                    repository.postVoteData(myVote);
                    voteData.observe(getViewLifecycleOwner(), observer);
                    editor.putInt("vote", vote);
                    editor.apply();
                }
            });
            nopeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vote = 0;
                    myVote.setValue(vote);
                    myVote.setImage_id(id);
                    mainViewModelVote.loadVoteData(repository);
                    voteData = mainViewModelVote.getVoteData();
                    repository.postVoteData(myVote);
                    voteData.observe(getViewLifecycleOwner(), observer);
                    editor.putInt("vote", vote);
                    editor.apply();
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_vote, container, false);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels / 2;
        voteImg = view.findViewById(R.id.vote_image);
        voteImg.getLayoutParams().height = height;
        loveButton = view.findViewById(R.id.love_button);
        nopeButton = view.findViewById(R.id. nope_button);
        faveButton = view.findViewById(R.id. fave_button);
        myFave = new MyFave();
        myVote = new MyVote();
        sharedPreferences = getContext().getSharedPreferences("Prefs", MODE_PRIVATE);
        editor =  sharedPreferences.edit();
        myVote.setSub_id(sharedPreferences.getString("name", null));
        myFave.setSub_id(sharedPreferences.getString("name", null));
        mainViewModelVote = ViewModelProviders.of(getActivity()).get(MainViewModelVote.class);
        repository = ((CatsApp)getActivity().getApplication()).getRepository();
        mainViewModelVote.loadVoteData(repository);
        voteData = mainViewModelVote.getVoteData();
        repository.postVoteData(myVote);
        voteData.observe(getViewLifecycleOwner(), this);
        faveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFave.setImage_id(sharedPreferences.getString("image_id", ""));
                repository.postFaveData(myFave);
                Log.e("Vote", ""+vote+" "+id);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
