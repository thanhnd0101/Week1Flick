package com.example.admin.week1projectflick.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.admin.week1projectflick.R;
import com.example.admin.week1projectflick.model.Movie;

import java.util.List;

public class ComplexRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Movie> movieList;

    private final int POPULAR=0,LESS_POPULAR=-1;
    private final Double PopularRate = 7.0;

    public ComplexRecyclerViewAdapter(Context context,List<Movie> movieList){
        this.mContext=context;
        this.movieList=movieList;
    }

    @Override
    public int getItemCount() {
        return this.movieList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(movieList.get(position).getVoteAverage() >= PopularRate){
            return POPULAR;
        }
        return LESS_POPULAR;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        switch (viewType){
            case POPULAR:
                View viewPop=inflater.inflate(R.layout.movie_card_popular,viewGroup,false);
                viewHolder=new PopularMovieViewHolder(viewPop,mContext,movieList);
                break;
                default:
                    View viewLesPop=inflater.inflate(R.layout.movie_card,viewGroup,false);
                    viewHolder=new LessPopularMovieViewHolder(viewLesPop,mContext,movieList);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()){
            case POPULAR:
                PopularMovieViewHolder viewPop=(PopularMovieViewHolder) viewHolder;
                configurePopViewHolder(viewPop,position);
                break;
                default:
                    LessPopularMovieViewHolder viewLesPop=(LessPopularMovieViewHolder)viewHolder;
                    configureLesPopViewHolder(viewLesPop,position);
        }
    }
    private void configurePopViewHolder(PopularMovieViewHolder viewHolder, int position){
        RequestOptions requestOptions=new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(100));

        if(mContext.getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT) {
            Glide.with(mContext)
                    .load(movieList.get(position).getPosterPath())
                    .apply(requestOptions)
                    .into(viewHolder.getThumbnailPopular());
        }else{
            Glide.with(mContext)
                    .load(movieList.get(position).getBackdroPath())
                    .apply(requestOptions)
                    .into(viewHolder.getThumbnailPopular());
        }
    }
    private void configureLesPopViewHolder(LessPopularMovieViewHolder viewHolder, int position){
        viewHolder.title.setText(movieList.get(position).getOriginalTitle());
        String vote = Float.toString(movieList.get(position).getVoteAverage());
        viewHolder.userRating.setText(vote);
        viewHolder.overView.setText(movieList.get(position).getOverview());
        RequestOptions requestOptions=new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(100));

        if(mContext.getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT) {
            Glide.with(mContext)
                    .load(movieList.get(position).getPosterPath())
                    .apply(requestOptions)
                    .into(viewHolder.getThumbnail());
        }else{
            Glide.with(mContext)
                    .load(movieList.get(position).getBackdroPath())
                    .apply(requestOptions)
                    .into(viewHolder.getThumbnail());
        }
    }
}
