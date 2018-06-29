package com.example.admin.week1projectflick.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.week1projectflick.DeTailActivity;
import com.example.admin.week1projectflick.R;
import com.example.admin.week1projectflick.model.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LessPopularMovieViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.title) TextView title;
    @BindView(R.id.userrating) TextView userRating;
    @BindView(R.id.overview) TextView overView;
    @BindView(R.id.thumbnail) ImageView thumbnail;


    public LessPopularMovieViewHolder(View view,final Context context,final List<Movie> movieList){
        super(view);
        ButterKnife.bind(this,view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=getAdapterPosition();
                if(pos !=RecyclerView.NO_POSITION){
                    Movie clickedMovie=movieList.get(pos);
                    Intent intent = new Intent(context, DeTailActivity.class);

                    intent.putExtra("id",movieList.get(pos).getId());
                    intent.putExtra("original_title",movieList.get(pos).getOriginalTitle());
                    intent.putExtra("poster_path",movieList.get(pos).getPosterPath());
                    intent.putExtra("backdrop_path",movieList.get(pos).getBackdroPath());
                    intent.putExtra("overview",movieList.get(pos).getOverview());
                    intent.putExtra("vote_average",movieList.get(pos).getVoteAverage());
                    intent.putExtra("release_date",movieList.get(pos).getReleaseDate());
                    intent.putExtra("popular_video",false);

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    Toast.makeText(view.getContext(),"You clicked "+movieList.get(pos).getOriginalTitle(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getUserrating() {
        return userRating;
    }

    public void setUserrating(TextView userrating) {
        this.userRating = userrating;
    }

    public TextView getOverView() {
        return overView;
    }

    public void setOverView(TextView overView) {
        this.overView = overView;
    }

    public ImageView getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ImageView thumbnail) {
        this.thumbnail = thumbnail;
    }
}
