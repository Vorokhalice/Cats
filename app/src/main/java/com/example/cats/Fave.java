package com.example.cats;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Fave extends Fragment implements Observer<List<FaveEntity>> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Repository repository;
    private RecyclerView recyclerView;
    private MainViewModelFave mainViewModelFave;
    private LiveData<List<FaveEntity>> faveData;
    public static Context contextOfFave;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Fave() {
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
    public void onChanged(List<FaveEntity> faveEntities) {
        ((FaveAdapter)recyclerView.getAdapter()).changeData(faveEntities);
        Log.e("FAVE", "ONCHANGED");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fave, container, false);
        contextOfFave = getContext();
        mainViewModelFave = ViewModelProviders.of(this).get(MainViewModelFave.class);
        repository = new Repository(this.getContext());
        mainViewModelFave.loadFaveData(repository);
        faveData = mainViewModelFave.getFaveData();
        faveData.observe(this, this);
        recyclerView = view.findViewById(R.id.rec_list1);
        LinearLayoutManager linearManager = new LinearLayoutManager(this.getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearManager);
        recyclerView.setAdapter(new FaveAdapter(new ArrayList<FaveEntity>()));
        return view;
    }
    public static Context getContextOfFave(){
        return contextOfFave;
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
