package com.example.cats;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class Vote extends Fragment implements Observer<VoteEntity> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
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
    Observer<VoteEntity> observer = new Observer<VoteEntity>() {
        @Override
        public void onChanged(VoteEntity voteEntity) {
            if (voteEntity != null) {
                Picasso.get().load(voteEntity.voteImageUrl).into(voteImg);
                url = voteEntity.voteImageUrl;
                editor.putString("url", url);
                editor.putString("image_id", voteEntity.voteImageId);
                editor.apply();
            }
        }
    };
    public static Context contextOfVote;
    private VoteEntity voteEntity;
    private String id;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Vote() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onChanged(VoteEntity voteEntity) {
        if (voteEntity != null) {
            vote = sharedPreferences.getInt("vote", -1);
            url = sharedPreferences.getString("url", "");
            id = sharedPreferences.getString("image_id", "");
            if (vote == -1) {
                url = voteEntity.voteImageUrl;
                Picasso.get().load(url).into(voteImg);
                editor.putString("url", url);
                editor.putString("image_id",voteEntity.voteImageId);
                editor.putInt("vote", vote);
                editor.apply();
                vote = 2;
                editor.apply();
            }
            else Picasso.get().load(url).into(voteImg);
            id = voteEntity.voteImageId;
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
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_vote, container, false);
        contextOfVote = getContext();
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
        sharedPreferences = contextOfVote.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor =  sharedPreferences.edit();
        myVote.setSub_id(sharedPreferences.getString("name", null));
        myFave.setSub_id(sharedPreferences.getString("name", null));
        mainViewModelVote = ViewModelProviders.of(getActivity()).get(MainViewModelVote.class);
        repository = new Repository(contextOfVote);
        mainViewModelVote.loadVoteData(repository);
        voteData = mainViewModelVote.getVoteData();
        repository.postVoteData(myVote);
        voteData.observe(getViewLifecycleOwner(), this);
        faveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFave.setImage_id(sharedPreferences.getString("image_id", ""));
                repository.postFaveData(myFave);
                Log.e("Vote", ""+vote+""+id);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
