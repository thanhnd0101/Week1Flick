package com.example.admin.week1projectflick.model;


import android.content.Context;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.admin.week1projectflick.R;

import butterknife.ButterKnife;


public class MovieDetailTab extends Fragment {
    static TextView plotsynopsis;
    static TextView releaseDate;
    static RatingBar userRating;

    private static String synopsis;
    private Float rating;
    private String dateOfRelease;

    private static String OverView;
    private static String ReleaseDate;
    private static Float UserRating;

    public MovieDetailTab() {
    }

    public static MovieDetailTab newInstance(String overview,String dateOfRelease,Float rating) {
        MovieDetailTab fragment = new MovieDetailTab();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        ReleaseDate=dateOfRelease;
        OverView=overview;
        UserRating=rating;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ButterKnife.bind(this, container);

        View view = inflater.inflate(R.layout.fragment_movie_detail_tab, container, false);

        plotsynopsis=view.findViewById(R.id.plotSynopsis);
        releaseDate=view.findViewById(R.id.releaseDate);
        userRating=view.findViewById(R.id.userrating);


        plotsynopsis.setText(OverView);
        releaseDate.setText(ReleaseDate);
        userRating.setRating(UserRating);

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

//    public void setPlotsynopsis(String plotsynopsis) {
//        this.plotsynopsis.setText(plotsynopsis);
//    }
//
//    public void setReleaseDate(String releaseDate) {
//        this.releaseDate.setText(releaseDate);
//    }
//
//    public void setUserRating(Float userRating) {
//        this.userRating.setRating(userRating);
//    }
}
