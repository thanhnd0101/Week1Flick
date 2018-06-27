package com.example.admin.week1projectflick.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.week1projectflick.BuildConfig;
import com.example.admin.week1projectflick.DeTailActivity;
import com.example.admin.week1projectflick.R;
import com.example.admin.week1projectflick.api.Client;
import com.example.admin.week1projectflick.api.Service;
import com.example.admin.week1projectflick.model.Movie;
import com.example.admin.week1projectflick.model.TrailerResponse;
import com.example.admin.week1projectflick.model.Youtube;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularMovieViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.thumbnail_poster_popular)  ImageView thumbnailPopular;

    public PopularMovieViewHolder(View view, final Context context, final List<Movie> movieList) {
        super(view);
        ButterKnife.bind(this, view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    Movie clickedMovie = movieList.get(pos);

                    Intent intent = new Intent(context, DeTailActivity.class);

                    intent.putExtra("id", clickedMovie.getId());
                    intent.putExtra("original_title", clickedMovie.getOriginalTitle());
                    intent.putExtra("poster_path", clickedMovie.getPosterPath());
                    intent.putExtra("backdrop_path", clickedMovie.getBackdroPath());
                    intent.putExtra("overview", clickedMovie.getOverview());
                    intent.putExtra("vote_average", movieList.get(pos).getVoteAverage());
                    intent.putExtra("release_date", clickedMovie.getReleaseDate());
                    intent.putExtra("popular_video",true);

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                    Toast.makeText(view.getContext(), "You clicked " + clickedMovie.getOriginalTitle(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public ImageView getThumbnailPopular() {
        return thumbnailPopular;
    }

    public void setThumbnailPopular(ImageView thumbnailPopular) {
        this.thumbnailPopular = thumbnailPopular;
    }

}
