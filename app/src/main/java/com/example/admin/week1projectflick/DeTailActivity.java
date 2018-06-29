package com.example.admin.week1projectflick;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.widget.ImageView;

import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.admin.week1projectflick.adapter.PagerAdapterInDetail;
import com.example.admin.week1projectflick.api.Client;
import com.example.admin.week1projectflick.api.Service;
import com.example.admin.week1projectflick.model.MovieDetailTab;
import com.example.admin.week1projectflick.model.MovieTrailerTab;
import com.example.admin.week1projectflick.model.TrailerResponse;
import com.example.admin.week1projectflick.model.Youtube;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.admin.week1projectflick.adapter.BindingAdapterUtils.loadImage;


public class DeTailActivity extends AppCompatActivity{
    @BindView(R.id.thumbnailImageHeader)
    ImageView poster;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    private String movieName;
    private List<Youtube> youtubes;
    private final String MyYouTubeApiKey = "AIzaSyDKjSlBvJyQwGKpmSjq8d7k0QZ-u7fpThM";
    private Boolean popularVideo = false;

    private static final String original_title = "original_title";
    private static final String poster_path = "poster_path";
    private static final String backdrop_path = "backdrop_path";
    private static final String overview = "overview";
    private static final String vote_average = "vote_average";
    private static final String release_date = "release_date";
    private static final String trailers="trailers";
    private static final String id="id";

    private String thumbnail_portrait;
    private String thumbnail_landscape;
    private String synopsis;
    private Float rating;
    private String dateOfRelease;
    private int idmovie;

    @BindView(R.id.trailer_recyclerview)
    RecyclerView trailer_recyclerView;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(original_title)) {

            if (savedInstanceState == null) {
                popularVideo = (Boolean) getIntent().getExtras().getBoolean("popular_video");
                assignInformation();
                LoadTrailerJSON();
            }else{
                popularVideo=false;
                youtubes=(List<Youtube>) savedInstanceState.getSerializable(trailers);
                idmovie=savedInstanceState.getInt(id);
                thumbnail_portrait=savedInstanceState.getString(poster_path);
                thumbnail_landscape=savedInstanceState.getString(backdrop_path);
                synopsis=savedInstanceState.getString(overview);
                rating=savedInstanceState.getFloat(vote_average);
                dateOfRelease=savedInstanceState.getString(release_date);
                movieName=savedInstanceState.getString(original_title);

                PagerAdapterInDetail pagerAdapterInDetail=new PagerAdapterInDetail(getSupportFragmentManager());
                pagerAdapterInDetail.addFragment(MovieDetailTab.newInstance(synopsis,dateOfRelease,rating),"Detail");
                pagerAdapterInDetail.addFragment(MovieTrailerTab.newInstance(this,youtubes,popularVideo),"Trailer");


                viewPager.setAdapter(pagerAdapterInDetail);
                tabLayout.setupWithViewPager(viewPager);

            }

            if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                Glide.with(this)
                        .load(thumbnail_portrait)
                        .into(poster);
            }else{
                loadImage(this,poster,thumbnail_landscape);
            }

        } else {
            Toast.makeText(this, "No API Data", Toast.LENGTH_SHORT).show();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initCollapsingToolbar();
    }

    public Activity getActivity() {
        Context context = this;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);
        collapsingToolbarLayout.setTitle(movieName);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(movieName);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(movieName);
                }
            }
        });
    }

    private void LoadTrailerJSON() {

        final int id_movie = getIntent().getExtras().getInt("id");
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

                    PagerAdapterInDetail pagerAdapterInDetail=new PagerAdapterInDetail(getSupportFragmentManager());
                    pagerAdapterInDetail.addFragment(MovieDetailTab.newInstance(synopsis,dateOfRelease,rating),"Detail");
                    pagerAdapterInDetail.addFragment(MovieTrailerTab.newInstance(getApplicationContext(),youtubes,popularVideo),"Trailer");

                    viewPager.setAdapter(pagerAdapterInDetail);
                    tabLayout.setupWithViewPager(viewPager);

                }

                @Override
                public void onFailure(Call<TrailerResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(DeTailActivity.this, "Error fetching trailer data...", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isFullScreenFirstTime", popularVideo);
        outState.putString(original_title, movieName);
        outState.putString(poster_path,thumbnail_portrait);
        outState.putString(backdrop_path,thumbnail_landscape);
        outState.putString(overview,synopsis);
        outState.putFloat(vote_average,rating);
        outState.putString(release_date,dateOfRelease);
        outState.putInt(id,idmovie);
        outState.putSerializable(trailers, (Serializable) youtubes);
    }

    private void assignInformation(){
        idmovie=getIntent().getExtras().getInt(id);
        thumbnail_portrait = getIntent().getExtras().getString(poster_path);
        thumbnail_landscape = getIntent().getExtras().getString(backdrop_path);
        movieName = (String) getIntent().getExtras().getString(original_title);
        synopsis = getIntent().getExtras().getString(overview);
        rating = getIntent().getExtras().getFloat(vote_average);
        dateOfRelease = getIntent().getExtras().getString(release_date);
    }
}
