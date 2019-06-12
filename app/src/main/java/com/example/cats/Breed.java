package com.example.cats;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Breed extends Fragment implements Observer<List<BreedsEntity>>/*, Observer */{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SharedPreferences prefs;
    private MainViewModelBreed mainViewModelBreed;
    private Repository repository;
    private String name;
    private String url;
    private TextView breed_name;
    private TextView breed_temperament;
    private TextView breed_description;
    private TextView breed_weight;
    private TextView breed_life;
    private TextView breed_wikipedia;
    private RatingBar affect;
    private RatingBar adapt;
    private RatingBar child;
    private RatingBar dog;
    private RatingBar energy;
    private RatingBar groom;
    private RatingBar health;
    private RatingBar shed;
    private RatingBar smart;
    private RatingBar social;
    private RatingBar stranger;
    private RatingBar vocal;
    private ImageView img;
    private Spinner spinner;
    private int pos;
    private String name_prev = "";
    private List<String> data = new ArrayList<String>();
    private LiveData<List<BreedsEntity>> breedsData;
    private RemoteDataSource remoteDataSource = new RemoteDataSource();
    private Observer<BreedEntity> observer = new Observer<BreedEntity>() {
        @Override
        public void onChanged(BreedEntity breedEntity) {
            url = prefs.getString("url", "");
            if (breedEntity != null) {
                Log.e("breed", breedEntity.imageBreedUrl);
                url = breedEntity.imageBreedUrl;
                Picasso.get().load(url).into(img);
            }
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("url", url);
            editor.apply();
        }
    };
    private LiveData<BreedEntity> breedData;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Breed() {
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
    public void onChanged(final List<BreedsEntity> breedEntities) {
        if (breedEntities != null) {
            data.clear();
            for (BreedsEntity breedsEntity : breedEntities) {
                data.add(breedsEntity.breedName);
            }
            if (breedEntities.size() > 0 && data.size() > 0) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, data);
                adapter.notifyDataSetChanged();
                spinner.setAdapter(adapter);
                spinner.setPrompt("Breed");
                spinner.setSelection(prefs.getInt("position",0));
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               final int position, long id) {
                        Log.e("Breed", "position " + position);
                        pos = position;
                        name = data.get(position);
                        breed_name.setText(name);
                        breed_temperament.setText(breedEntities.get(position).temp);
                        breed_description.setText(breedEntities.get(position).descr);
                        breed_life.setText("Life span: "+breedEntities.get(position).lf + " years");
                        breed_weight.setText(breedEntities.get(position).w+" kg");
                        breed_wikipedia.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View arg0) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(String.valueOf(breedEntities.get(position).wiki)));
                                startActivity(intent);
                            }
                        });
                        affect.setNumStars(breedEntities.get(position).aff);
                        adapt.setNumStars(breedEntities.get(position).adapt);
                        child.setNumStars(breedEntities.get(position).child);
                        dog.setNumStars(breedEntities.get(position).dog);
                        energy.setNumStars(breedEntities.get(position).en);
                        groom.setNumStars(breedEntities.get(position).groom);
                        health.setNumStars(breedEntities.get(position).healthis);
                        smart.setNumStars(breedEntities.get(position).intell);
                        shed.setNumStars(breedEntities.get(position).shed);
                        social.setNumStars(breedEntities.get(position).socio);
                        stranger.setNumStars(breedEntities.get(position).stranger);
                        vocal.setNumStars(breedEntities.get(position).vocal);
                        mainViewModelBreed.loadBreedData(repository, breedEntities.get(position).breedId);
                        breedData = mainViewModelBreed.getBreedData();
                        breedData.observe(getViewLifecycleOwner(), observer);
                        Log.e("BreedData", "" + breedData + breedEntities.get(position).breedId);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("position", position);
                        editor.apply();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_breed, container, false);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels / 3;
        prefs = getContext().getSharedPreferences("Prefs", MODE_PRIVATE);
        mainViewModelBreed = ViewModelProviders.of(this).get(MainViewModelBreed.class);
        repository = new Repository(this.getContext());
        mainViewModelBreed.loadBreedsData(repository);
        breedsData = mainViewModelBreed.getBreedsData();
        breedsData.observe(this, this);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        img = view.findViewById(R.id.breed_image);
        breed_name = view.findViewById(R.id.breed_name);
        breed_temperament = view.findViewById(R.id.breed_temperament);
        breed_description = view.findViewById(R.id.breed_description);
        breed_weight = view.findViewById(R.id.breed_weight);
        breed_life = view.findViewById(R.id.breed_life);
        breed_wikipedia = view.findViewById(R.id.breed_wikipedia);
        affect = view.findViewById(R.id.rating_affect);
        adapt = view.findViewById(R.id.rating_adapt);
        child = view.findViewById(R.id.rating_child);
        dog = view.findViewById(R.id.rating_dog);
        energy = view.findViewById(R.id.rating_energy);
        groom = view.findViewById(R.id.rating_groom);
        health = view.findViewById(R.id.rating_health);
        shed = view.findViewById(R.id.rating_shed);
        smart = view.findViewById(R.id.rating_smart);
        social = view.findViewById(R.id.rating_social);
        stranger = view.findViewById(R.id.rating_stranger);
        vocal = view.findViewById(R.id.rating_vocal);
        img.getLayoutParams().height = height;



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
