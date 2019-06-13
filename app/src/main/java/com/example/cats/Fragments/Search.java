package com.example.cats.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.cats.CatsApp;
import com.example.cats.mainViewModels.MainViewModelSearch;
import com.example.cats.R;
import com.example.cats.data.Repository;
import com.example.cats.adapters.SearchAdapter;
import com.example.cats.entities.CategoriesEntity;
import com.example.cats.entities.CategoryEntity;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Search extends Fragment implements Observer<List<CategoriesEntity>> {
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
    private String url;
    private Observer<List<CategoryEntity>> observer = new Observer<List<CategoryEntity>>() {
        @Override
        public void onChanged(List<CategoryEntity> categoryEntity) {
            if (categoryEntity != null) {
                ((SearchAdapter)recyclerView.getAdapter()).changeData(categoryEntity);
            }
        }
    };

    public Search() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        contextOfSearch = getContext();
        repository = ((CatsApp)getActivity().getApplication()).getRepository();
        prefs = contextOfSearch.getSharedPreferences("Prefs", MODE_PRIVATE);;
        recyclerView = view.findViewById(R.id.rec_list);
        LinearLayoutManager linearManager = new LinearLayoutManager(this.getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearManager);
        recyclerView.setAdapter(new SearchAdapter(new ArrayList<CategoryEntity>(), repository));
        mainViewModelSearch = ViewModelProviders.of(this).get(MainViewModelSearch.class);
        mainViewModelSearch.loadCategoriesData(repository);
        categoriesData = mainViewModelSearch.getCategoriesData();
        categoriesData.observe(this, this);
        spinner = view.findViewById(R.id.category_spinner);
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
