package com.example.admin.week1projectflick;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.week1projectflick.api.Client;
import com.example.admin.week1projectflick.api.Service;
import com.example.admin.week1projectflick.model.TrailerResponse;
import com.example.admin.week1projectflick.model.Youtube;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DeTailActivity extends AppCompatActivity {
    @BindView(R.id.plotSynopsis) TextView  plotSynopsis;
    @BindView(R.id.releaseDate) TextView releaseDate;
    @BindView(R.id.userrating) RatingBar userRating;
    @BindView(R.id.thumbnailImageHeader) ImageView imageView;
    String movieName;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initCollapsingToolbar();

        Intent intentThatStartedThisActivity = getIntent();
        if(intentThatStartedThisActivity.hasExtra("original_title")){
            String thumbnail;
            if(getActivity().getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
                thumbnail = getIntent().getExtras().getString("poster_path");
            }else{
                thumbnail = getIntent().getExtras().getString("backdrop_path");
            }
            movieName = (String)getIntent().getExtras().getString("original_title");
            String synopsis=getIntent().getExtras().getString("overview");
            Float rating= getIntent().getExtras().getFloat("vote_average");
            String dateOfRelease= getIntent().getExtras().getString("release_date");

            Glide.with(this)
                    .load(thumbnail)
                    .into(imageView);

            plotSynopsis.setText(synopsis);
            userRating.setRating(rating);
            releaseDate.setText(dateOfRelease);

            LoadTrailerJSON();
        }else{
            Toast.makeText(this,"No API Data",Toast.LENGTH_SHORT).show();
        }
    }

    public Activity getActivity(){
        Context context=this;
        while(context instanceof ContextWrapper){
            if(context instanceof Activity){
                return (Activity)context;
            }
            context=((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    private void initCollapsingToolbar(){
        final CollapsingToolbarLayout collapsingToolbarLayout=
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        AppBarLayout appBarLayout=(AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);
        collapsingToolbarLayout.setTitle(movieName);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow=false;
            int scrollRange=-1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(scrollRange==-1){
                    scrollRange=appBarLayout.getTotalScrollRange();
                }
                if(scrollRange+ verticalOffset == 0){
                    collapsingToolbarLayout.setTitle(movieName);
                    isShow=true;
                }
                else if (isShow){
                    collapsingToolbarLayout.setTitle(movieName);
                }
            }
        });
    }

    private List<Youtube> youtubes;
    private final String MyYouTubeApiKey="AIzaSyDKjSlBvJyQwGKpmSjq8d7k0QZ-u7fpThM";

    private void LoadTrailerJSON(){

        int id_movie=getIntent().getExtras().getInt("id");
        try {
            //Tao object retrofit
            Client client = new Client();
            //Tao interface
            Service apiService = client.getClient().create(Service.class);

            Call<TrailerResponse> call = apiService.getTrailerMovie(id_movie, BuildConfig.THE_MOVIE_DB_API_TOKEN);

            call.enqueue(new Callback<TrailerResponse>() {
                @Override
                public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                    youtubes = response.body().getYoutubes();
                    initTrailer();
                }

                @Override
                public void onFailure(Call<TrailerResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(DeTailActivity.this, "Error fetching trailer data...", Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            Log.d("Error",e.getMessage());
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    private void initTrailer(){
        YouTubePlayerFragment youTubePlayerFragment= (YouTubePlayerFragment)
                getFragmentManager().findFragmentById(R.id.youtubeFragment);
        youTubePlayerFragment.initialize(MyYouTubeApiKey,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                        if(youtubes.size()>0){youTubePlayer.cueVideo(youtubes.get(0).getSource());
                        youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                            @Override
                            public void onLoading() {

                            }

                            @Override
                            public void onLoaded(String s) {

                            }

                            @Override
                            public void onAdStarted() {

                            }

                            @Override
                            public void onVideoStarted() {
                                youTubePlayer.setFullscreen(true);
                            }

                            @Override
                            public void onVideoEnded() {

                            }

                            @Override
                            public void onError(YouTubePlayer.ErrorReason errorReason) {

                            }
                        });
                        }
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }
}
