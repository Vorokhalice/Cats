package com.example.cats.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cats.CatsApp;
import com.example.cats.mainViewModels.MainViewModelFave;
import com.example.cats.R;
import com.example.cats.data.Repository;
import com.example.cats.adapters.FaveAdapter;
import com.example.cats.entities.FaveEntity;

import java.util.ArrayList;
import java.util.List;

public class Fave extends Fragment implements Observer<List<FaveEntity>> {
    private Repository repository;
    private RecyclerView recyclerView;
    private MainViewModelFave mainViewModelFave;
    private LiveData<List<FaveEntity>> faveData;
    public static Context contextOfFave;

    private OnFragmentInteractionListener mListener;

    public Fave() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onChanged(List<FaveEntity> faveEntities) {
        ((FaveAdapter)recyclerView.getAdapter()).changeData(faveEntities);
        Log.e("FAVE", "ONCHANGED");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fave, container, false);
        contextOfFave = getContext();
        mainViewModelFave = ViewModelProviders.of(this).get(MainViewModelFave.class);
        repository = ((CatsApp)getActivity().getApplication()).getRepository();
        mainViewModelFave.loadFaveData(repository);
        faveData = mainViewModelFave.getFaveData();
        faveData.observe(this, this);
        recyclerView = view.findViewById(R.id.rec_list1);
        LinearLayoutManager linearManager = new LinearLayoutManager(this.getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearManager);
        recyclerView.setAdapter(new FaveAdapter(new ArrayList<FaveEntity>(), repository));
        return view;
    }
    public static Context getContextOfFave(){
        return contextOfFave;
    }

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
        void onFragmentInteraction(Uri uri);
    }
}
