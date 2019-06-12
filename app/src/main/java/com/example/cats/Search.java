package com.example.cats;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
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

import static android.content.Context.MODE_PRIVATE;

public class Search extends Fragment implements Observer<List<CategoriesEntity>> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<String> data = new ArrayList<String>();
    private LiveData<List<CategoriesEntity>> categoriesData;
    private LiveData<List<CategoryEntity>> categoryData;
    public static Context contextOfSearch;
    private MainViewModelSearch mainViewModelSearch;
    private Repository repository;
    private RecyclerView recyclerView;
    private Spinner spinner;
    private SharedPreferences prefs;
    private int cat_id;
    private int pos = 0;
    private String url;
    private Observer<List<CategoryEntity>> observer = new Observer<List<CategoryEntity>>() {
        @Override
        public void onChanged(List<CategoryEntity> categoryEntity) {
            if (categoryEntity != null) {
                ((SearchAdapter)recyclerView.getAdapter()).changeData(categoryEntity);
            }
        }
    };

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Search() {
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
    public void onChanged(final List<CategoriesEntity> categoriesEntities) {
        if (categoriesEntities != null) {
            data.clear();
            data.add("None");
            for (CategoriesEntity categoriesEntity : categoriesEntities) {
                data.add(categoriesEntity.category_name);
            }
            if (categoriesEntities.size() > 0 && data.size() > 0) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, data);
                adapter.notifyDataSetChanged();
                spinner.setAdapter(adapter);
                spinner.setPrompt("Category");
                spinner.setSelection(prefs.getInt("searchposition",0));
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                        if (data.get(position).equals("None")) {
                            mainViewModelSearch.loadNonCategoryData(repository, -1);
                            categoryData = mainViewModelSearch.getNonCategoryData();
                            categoryData.observe(getViewLifecycleOwner(), observer);
                        } else {
                            cat_id = categoriesEntities.get(position-1).category_id;
                            Repository repository = new Repository(getContext());
                            mainViewModelSearch.loadCategoryData(repository, cat_id);
                            categoryData = mainViewModelSearch.getCategoryData();
                            categoryData.observe(getViewLifecycleOwner(), observer);
                        }
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("searchposition", position);
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        contextOfSearch = getContext();
        prefs = contextOfSearch.getSharedPreferences("Prefs", MODE_PRIVATE);;
        recyclerView = view.findViewById(R.id.rec_list);
        LinearLayoutManager linearManager = new LinearLayoutManager(this.getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearManager);
        recyclerView.setAdapter(new SearchAdapter(new ArrayList<CategoryEntity>()));
        mainViewModelSearch = ViewModelProviders.of(this).get(MainViewModelSearch.class);
        repository = new Repository(this.getContext());
        mainViewModelSearch.loadCategoriesData(repository);
        categoriesData = mainViewModelSearch.getCategoriesData();
        categoriesData.observe(this, this);
        Log.e("Search", ""+categoriesData);
        spinner = view.findViewById(R.id.category_spinner);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
