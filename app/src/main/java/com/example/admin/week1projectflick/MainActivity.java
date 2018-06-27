package com.example.admin.week1projectflick;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.week1projectflick.adapter.ComplexRecyclerViewAdapter;
import com.example.admin.week1projectflick.api.Client;
import com.example.admin.week1projectflick.api.Service;
import com.example.admin.week1projectflick.model.Movie;
import com.example.admin.week1projectflick.model.MovieResponse;
import com.example.admin.week1projectflick.model.TrailerResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private ComplexRecyclerViewAdapter adapter;
    private List<Movie> movieList;
    ProgressDialog pd;
    @BindView(R.id.main_content)
    SwipeRefreshLayout swipeContainer;

    private static final String movie_list_saved = "movie_list";
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            movieList=(List<Movie>)savedInstanceState.getSerializable(movie_list_saved);
            if(movieList.size()> 0){
                Log.d("asdasdas","size: " + movieList.size());
            }
            setRecyclerViewLayout();

            adapter = new ComplexRecyclerViewAdapter(getApplicationContext(), movieList);
            recyclerView.setAdapter(adapter);
        }
        if(savedInstanceState==null){
            initViews();
        }

        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initViews();
                Toast.makeText(MainActivity.this, "Movies Refreshed", Toast.LENGTH_SHORT).show();
            }
        });
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

    private void initViews() {
        pd = new ProgressDialog(this);
        pd.setMessage("Fectching movies...");
        pd.setCancelable(false);
        pd.show();

        //movieList = new ArrayList<>();
        //adapter = new ComplexRecyclerViewAdapter(this, movieList);

        setRecyclerViewLayout();

        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();

        loadJSON();
    }
    private void setRecyclerViewLayout(){

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        }
    }
    private void loadJSON() {
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please obtain API key firstly from themoviedb.org", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                return;
            }
            //Tao object retrofit
            Client client = new Client();
            //Tao interface
            Service apiService = client.getClient().create(Service.class);

            Call<MovieResponse> call = apiService.getNowPlayingMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);

            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                    movieList = response.body().getResults();

                    adapter=new ComplexRecyclerViewAdapter(getApplicationContext(), movieList);

                    recyclerView.setAdapter(adapter);

                    if (swipeContainer.isRefreshing()) {
                        swipeContainer.setRefreshing(false);
                    }
                    pd.dismiss();
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(MainActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
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
        outState.putSerializable(movie_list_saved, (Serializable) movieList);
    }
}
