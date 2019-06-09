package com.example.cats;

import android.content.Context;
import android.content.Intent;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Breed.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Breed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Breed extends Fragment implements Observer<List<BreedsEntity>>/*, Observer */{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String name;
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
    //private ImageService imageService;
    private String[] items;
    private Spinner spinner;
    //private BreedService breedService;
    private List<String> data = new ArrayList<String>();
    List<String> ids = new ArrayList<String>();
    private LiveData<List<BreedsEntity>> breedsData;
    private RemoteDataSource remoteDataSource = new RemoteDataSource();
    private Observer<BreedEntity> observer = new Observer<BreedEntity>() {
        @Override
        public void onChanged(BreedEntity breedEntity) {
            if (breedEntity != null) {
                Log.e("breed", breedEntity.imageBreedUrl);
                Picasso.get().load(breedEntity.imageBreedUrl).into(img);
            }
        }
    };
    private LiveData<BreedEntity> breedData;
    //String[] data = {"Abyssinian", "Aegean", "American Bobtail", "American Curl", "American Shorthair", "American Wirehair", "Arabian Mau", "Australian Mist", "Balinese", "Bambino", "Bengal", "Birman", "Bombay", "British Longhair", "British Shorthair", "Burmese", "Burmilla", "California Spangled"};

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Breed() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Breed.
     */
    // TODO: Rename and change types and number of parameters
    public static Breed newInstance(String param1, String param2) {
        Breed fragment = new Breed();
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
                // заголовок
                spinner.setPrompt("Breed");
                //breedsData.observe(this, this.get);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               final int position, long id) {
                        Log.e("Breed", "position " + position);
                        name = data.get(position);
                        breed_name.setText(name);
                        breed_temperament.setText(breedEntities.get(position).temp);
                        breed_description.setText(breedEntities.get(position).descr);
                        breed_life.setText(breedEntities.get(position).lf);
                        breed_weight.setText(breedEntities.get(position).w);
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
                        MainViewModel mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
                        Repository repository = new Repository(getContext());
                        //Picasso.get().load(remoteDataSource.getBreed(breedEntities.get(position).breedId).get(0).getUrl());
                        mainViewModel.loadBreedData(repository, breedEntities.get(position).breedId);
                        breedData = mainViewModel.getBreedData();
                        breedData.observe(getViewLifecycleOwner(), observer);
                        Log.e("BreedData", "" + breedData+breedEntities.get(position).breedId);
                        //Picasso.get().load(breedData).into(img);
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
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        Repository repository = new Repository(this.getContext());
        mainViewModel.loadBreedsData(repository);
        breedsData = mainViewModel.getBreedsData();
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
        // выделяем элемент
        //spinner.setSelection(0);
        // устанавливаем обработчик нажатия
       // Log.e("data", " "+data);
           /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item, data);
             //adapter.setDropDownViewResource(android.R.layout.test_list_item);
            Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
            spinner.setAdapter(adapter);
            // заголовок
            spinner.setPrompt("Breed");
            spinner.setSelection(0);
           /* spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    Log.e("Breed", "position " + position);
                    name = items[position];
                    breed_name.setText(name);
                /*final Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.thecatapi.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                breedService = retrofit.create(BreedService.class);
                breedService.getBreed(name, API_KEY).enqueue(new Callback<List<Breeds>>() {
                    @Override
                    public void onResponse(Call<List<Breeds>> call, Response<List<Breeds>> response) {
                        if (response.isSuccessful()) {
                            final List<Breeds> breeds = response.body();
                            breed_temperament.setText(String.valueOf(breeds.get(0).getTemperament()));
                            breed_description.setText(String.valueOf(breeds.get(0).getDescription()));
                            breed_weight.setText(String.valueOf(breeds.get(0).getWeight().getMetric()) + " kg");
                            breed_life.setText("Life span: " + String.valueOf(breeds.get(0).getLife_span()) + " years");
                            affect.setNumStars(breeds.get(0).getAffection_level());
                            adapt.setNumStars(breeds.get(0).getAdaptability());
                            child.setNumStars(breeds.get(0).getChild_friendly());
                            dog.setNumStars(breeds.get(0).getDog_friendly());
                            energy.setNumStars(breeds.get(0).getEnergy_level());
                            groom.setNumStars(breeds.get(0).getGrooming());
                            health.setNumStars(breeds.get(0).getHealth_issues());
                            smart.setNumStars(breeds.get(0).getIntelligence());
                            shed.setNumStars(breeds.get(0).getShedding_level());
                            social.setNumStars(breeds.get(0).getSocial_needs());
                            stranger.setNumStars(breeds.get(0).getStranger_friendly());
                            vocal.setNumStars(breeds.get(0).getVocalisation());
                            breed_wikipedia.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View arg0) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(String.valueOf(breeds.get(0).getWikipedia_url())));
                                    startActivity(intent);
                                }

                            });
                            String id = breeds.get(0).getId();
                            imageService = retrofit.create(ImageService.class);
                            imageService.getImage(id, API_KEY).enqueue(new Callback<List<Images>>() {
                                @Override
                                public void onResponse(Call<List<Images>> call, Response<List<Images>> response) {
                                    if (response.isSuccessful()) {
                                        List<Images> images = response.body();
                                        Log.e("Main", "img" + images.get(0).getUrl());
                                        Picasso.get().load(images.get(0).getUrl()).into(img);
                                    }
                                }
                                @Override
                                public void onFailure(Call<List<Images>> call, Throwable t) {
                                    Log.e("Breed", "exc" + t);
                                }
                            });
                            //Log.e("Main", "Получилось"+characteristiсs.get(0).getBreeds());
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Breeds>> call, Throwable t) {
                        Log.e("Main", "Exception" + t.toString());
                    }
                });
                }
                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });*/


        return view;
    }
   /* private void showListInSpinner(){
        //String array to store all the book names
        String[] items = new String[data.size()];

        //Traversing through the whole list to get all the names
        for(int i=0; i<data.size(); i++){
            //Storing names to string array
            items[i] = data.get(i).breedName;
        }

        //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
        //setting adapter to spinner
        spinner.setAdapter(adapter);
        //Creating an array adapter for list view

    }*/

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
   /* public interface BreedService {
        @GET("/v1/breeds/search")
        Call<List<Breeds>> getBreed(@Query("q")String name, @Header("x-api-key") String appID);
    }
    public interface ImageService {
        @GET("/v1/images/search")
        Call<List<Images>> getImage(@Query("breed_id")String name, @Header("x-api-key") String appID);
    }*/
}
