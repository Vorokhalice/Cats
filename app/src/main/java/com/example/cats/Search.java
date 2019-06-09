package com.example.cats;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
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
import android.widget.Spinner;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Search.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Search extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static String API_KEY = "19da309d-127a-4147-8bb8-4c0c05e39452";
    String[] data = {"none", "boxes", "clothes", "hats", "sinks","space", "sunglasses", "ties"};
    //String[] breeds =  {"None", "Abyssinian", "Aegean", "American Bobtail", "American Curl", "American Shorthair", "American Wirehair", "Arabian Mau", "Australian Mist", "Balinese", "Bambino", "Bengal", "Birman", "Bombay", "British Longhair", "British Shorthair", "Burmese", "Burmilla", "California Spangled"};
    private SearchService searchService;
    private ImagesService imagesService;
    public static Context contextOfSearch;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Search() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Search.
     */
    // TODO: Rename and change types and number of parameters
    public static Search newInstance(String param1, String param2) {
        Search fragment = new Search();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        contextOfSearch = getContext();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thecatapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ImageView search_image = view.findViewById(R.id.search_image);
        final RecyclerView recyclerView = view.findViewById(R.id.rec_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item, data);
        // adapter.setDropDownViewResource(android.R.layout.test_list_item);
        Spinner spinner = (Spinner) view.findViewById(R.id.category_spinner);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Category");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item, data);
        // adapter.setDropDownViewResource(android.R.layout.test_list_item);
        Spinner spinner2 = (Spinner) view.findViewById(R.id.breed_spinner);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Category");
        LinearLayoutManager linearManager = new LinearLayoutManager(this.getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearManager);
        recyclerView.setAdapter(new SearchAdapter(new ArrayList<Images>()));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String name = data[position];
                if (name.equals("none")) {
                    imagesService = retrofit.create(ImagesService.class);
                    imagesService.getAllImages(30, "random", API_KEY).enqueue(new Callback<List<Images>>() {
                        @Override
                        public void onResponse(Call<List<Images>> call, Response<List<Images>> response) {
                            if (response.isSuccessful()) {
                                final List<Images> images = response.body();
                                ((SearchAdapter)recyclerView.getAdapter()).changeData(images);
                                Log.e("Main", "Получилось"+images);
                            }
                        }
                        @Override
                        public void onFailure(Call<List<Images>> call, Throwable t) {
                            Log.e("Main", "Exception" + t.toString());
                        }
                    });
                }
                else {
                    int cat_id = 0;
                    if (name.equals("boxes")) {
                        cat_id = 5;
                    }
                    if (name.equals("clothes")) {
                        cat_id = 15;
                    }
                    if (name.equals("hats")) {
                        cat_id = 1;
                    }
                    if (name.equals("sinks")) {
                        cat_id = 14;
                    }
                    if (name.equals("space")) {
                        cat_id = 2;
                    }
                    if (name.equals("sunglasses")) {
                        cat_id = 4;
                    }
                    if (name.equals("ties")) {
                        cat_id = 7;
                    }
                    searchService = retrofit.create(SearchService.class);
                    searchService.getImages(30, "random", cat_id, API_KEY).enqueue(new Callback<List<Images>>() {
                        @Override
                        public void onResponse(Call<List<Images>> call, Response<List<Images>> response) {
                            if (response.isSuccessful()) {
                                final List<Images> images = response.body();
                                ((SearchAdapter)recyclerView.getAdapter()).changeData(images);
                                Log.e("Main", "Получилось"+images);
                            }
                        }
                        @Override
                        public void onFailure(Call<List<Images>> call, Throwable t) {
                            Log.e("Main", "Exception" + t.toString());
                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        return view;
    }
    public static Context getContextOfSearch(){
        return contextOfSearch;
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
    public interface SearchService{
        @GET("/v1/images/search")
        Call<List<Images>> getImages(@Query("limit") int limit,  @Query("order") String ord, @Query("category_ids") int category , @Header("x-api-key") String appID);
    }
    public interface ImagesService{
        @GET("/v1/images/search")
        Call<List<Images>> getAllImages(@Query("limit") int limit,  @Query("order") String ord, @Header("x-api-key") String appID);
    }
}
