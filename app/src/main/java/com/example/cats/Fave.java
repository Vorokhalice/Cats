package com.example.cats;

import android.content.Context;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fave.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fave#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fave extends Fragment implements Observer<List<FaveEntity>> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
   // private FaveService faveService;
    private static String API_KEY = "19da309d-127a-4147-8bb8-4c0c05e39452";
    private RecyclerView recyclerView;
    private LiveData<List<FaveEntity>> faveData;
    public static Context contextOfFave;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Fave() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fave.
     */
    // TODO: Rename and change types and number of parameters
    public static Fave newInstance(String param1, String param2) {
        Fave fragment = new Fave();
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
    public void onChanged(List<FaveEntity> faveEntities) {
        ((FaveAdapter)recyclerView.getAdapter()).changeData(faveEntities);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fave, container, false);
        contextOfFave = getContext();
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        Repository repository = new Repository(this.getContext());
        mainViewModel.loadFaveData(repository);
        faveData = mainViewModel.getFaveData();
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
