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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Vote.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Vote#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Vote extends Fragment /*implements Observer<VoteEntity>*/ {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
   // private ImageVoteService imageVoteService;
    private static String API_KEY = "19da309d-127a-4147-8bb8-4c0c05e39452";
    //private VoteService voteService;
    private MyVote myVote;
    private SharedPreferences sharedPreferences;
   // private PostVoteService postVoteService;
    private MyFave myFave;
    private ImageView voteImg;
    private LiveData<VoteEntity> voteData;
    Observer<VoteEntity> observer = new Observer<VoteEntity>() {
        @Override
        public void onChanged(VoteEntity voteEntity) {
            if (voteEntity != null) {
                Picasso.get().load(voteEntity.voteImageUrl).into(voteImg);
                id = voteEntity.voteImageId;
            }
        }
    };
    public static Context contextOfVote;
    private int vote;
    private VoteEntity voteEntity;
    private String id;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Vote() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Vote.
     */
    // TODO: Rename and change types and number of parameters
    public static Vote newInstance(String param1, String param2) {
        Vote fragment = new Vote();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /*@Override
    public void onChanged(VoteEntity voteEntity) {
        if (voteEntity != null) {
            Picasso.get().load(voteEntity.voteImageUrl).into(voteImg);
            id = voteEntity.voteImageId;
        }
    }*/

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
        final MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        final Repository repository = new Repository(contextOfVote);
        mainViewModel.loadVoteData(repository);
        voteData = mainViewModel.getVoteData();
        voteData.observe(this, observer);
        Button loveButton = view.findViewById(R.id.love_button);
        Button nopeButton = view.findViewById(R.id. nope_button);
        Button faveButton = view.findViewById(R.id. fave_button);
        myFave = new MyFave();
        myVote = new MyVote();
        sharedPreferences = contextOfVote.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        myVote.setSub_id(sharedPreferences.getString("name", null));
        myFave.setSub_id(sharedPreferences.getString("name", null));
        loveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myVote.setValue(1);
                myVote.setImage_id(id);
                repository.postVoteData(myVote);
                //mainViewModel.loadVoteData(repository);
                //voteData = mainViewModel.getVoteData();
                voteData.observe(getViewLifecycleOwner(), observer);
            }

                /*mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
                Repository repository = new Repository(getContext());
                mainViewModel.loadVoteData(repository);
                voteData = mainViewModel.getVoteData();
                voteData.observe(getViewLifecycleOwner(), new Observer<VoteEntity>() {
                    @Override
                    public void onChanged(VoteEntity voteEntity) {

                    }
                })
                /*myVote.setValue(1);
                myVote.setImage_id(id);
                voteService.postVote(myVote, API_KEY, "application/json").enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Log.e("Vote", "succeess");
                        }
                        else Log.e("Vote", "not success" + response);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("Vote", "Fail");
                    }
                });
                imageVoteService.getVoteImage(API_KEY).enqueue(new Callback<List<Images>>() {
                    @Override
                    public void onResponse(Call<List<Images>> call, Response<List<Images>> response) {
                        if (response.isSuccessful()) {
                            final List<Images> image = response.body();
                            Picasso.get().load(image.get(0).getUrl()).into(voteImg);
                            id = image.get(0).getId();
                        }
                    }
                    @Override
                    public void onFailure (Call <List<Images>> call, Throwable t){
                        Log.e("Main", "Exception" + t.toString());
                    }
                });
            }*/
        });
        nopeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myVote.setValue(0);
                myVote.setImage_id(id);
                repository.postVoteData(myVote);
                mainViewModel.loadVoteData(repository);
                voteData = mainViewModel.getVoteData();
            }
                /*voteService.postVote(myVote, API_KEY, "application/json").enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Log.e("Vote", "success");
                        }
                        else Log.e("Vote", "not success" + response);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("Vote", "Fail");
                    }
                });
                imageVoteService.getVoteImage(API_KEY).enqueue(new Callback<List<Images>>() {
                    @Override
                    public void onResponse(Call<List<Images>> call, Response<List<Images>> response) {
                        if (response.isSuccessful()) {
                            final List<Images> image = response.body();
                            Picasso.get().load(image.get(0).getUrl()).into(voteImg);
                            id = image.get(0).getId();
                        }
                    }
                    @Override
                    public void onFailure (Call <List<Images>> call, Throwable t){
                        Log.e("Main", "Exception" + t.toString());
                    }
                });
            }*/
        });
        faveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFave.setImage_id(id);
                repository.postFaveData(myFave);
            }
               /* postVoteService.postFave(API_KEY, "application/json", myFave).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }*/
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
