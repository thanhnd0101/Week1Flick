package com.example.admin.week1projectflick.model;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.admin.week1projectflick.R;
import com.example.admin.week1projectflick.adapter.TrailerRecyclerViewAdapter;
import java.util.List;



public class MovieTrailerTab extends Fragment {

    private RecyclerView trailerRecyclerView;
    private static Context ConText;
    private static List<Youtube> trailers;
    private static Boolean popularVideo;
    TrailerRecyclerViewAdapter trailerRecyclerViewAdapter;
    View view;

    public MovieTrailerTab() {
    }

    public static MovieTrailerTab newInstance(Context context, List<Youtube> trailer, Boolean popularvideo) {
        MovieTrailerTab fragment = new MovieTrailerTab();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        ConText=context;
        trailers=trailer;
        popularVideo=popularvideo;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.trailer_recylerview, container, false);

        trailerRecyclerView = (RecyclerView)view.findViewById(R.id.trailer_recyclerview);

        setRecyclerViewLayout();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        trailerRecyclerViewAdapter = new TrailerRecyclerViewAdapter(getActivity(),trailers,popularVideo);

        trailerRecyclerView.setAdapter(trailerRecyclerViewAdapter);
    }

    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public RecyclerView getTrailerrRecyclerView() {
        return trailerRecyclerView;
    }


    private void setRecyclerViewLayout(){
        trailerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
    }


}
