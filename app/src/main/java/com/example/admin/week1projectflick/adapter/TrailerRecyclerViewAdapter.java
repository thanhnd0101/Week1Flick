package com.example.admin.week1projectflick.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.week1projectflick.R;
import com.example.admin.week1projectflick.model.Youtube;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.util.List;

public class TrailerRecyclerViewAdapter extends RecyclerView.Adapter<TrailerViewHolder> {

    private Context context;
    private List<Youtube> trailers;
    private Boolean popularVideo;
    YouTubePlayerFragment youTubePlayerFragment;

    public TrailerRecyclerViewAdapter(Context context, List<Youtube> trailer, Boolean popularVideo) {
        this.context = context;
        this.trailers = trailer;
        this.popularVideo = popularVideo;
    }


    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        TrailerViewHolder trailerViewHolder;

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View view = inflater.inflate(R.layout.fragment_movie_trailer_tab, viewGroup, false);

        trailerViewHolder = new TrailerViewHolder(view,popularVideo);

        return trailerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder trailerViewHolder, int i) {
        trailerViewHolder.bind(trailers.get(i), getActivity());
    }


    @Override
    public int getItemCount() {
//        return this.trailers.size();
        return trailers.size()==0?0:1;
    }

    public Activity getActivity() {
        Context context = this.context;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }


}
