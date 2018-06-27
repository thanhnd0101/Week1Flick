package com.example.admin.week1projectflick.model;

import android.widget.Toast;

import com.example.admin.week1projectflick.MainActivity;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Movie implements Serializable{
    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("id")
    private int id;
    @SerializedName("video")
    private boolean video;
    @SerializedName("vote_average")
    private float voteAverage;
    @SerializedName("title")
    private String title;
    @SerializedName("popularity")
    private float popularity;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("original_language")
    private String originLanguage;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("genre_ids")
    private List<Integer> genreIds=new ArrayList<Integer>();
    @SerializedName("backdrop_path")
    private String backdroPath;
    @SerializedName("adult")
    private boolean adult;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;

    public Movie(int voteCount,int id,boolean video,float voteAverage,String title,float popularity,String posterPath,
                 String originLanguage,String originalTitle,List<Integer> genreIds,String backdroPath,boolean adult,
                 String overview,String releaseDate){
        this.voteCount=voteCount;
        this.id=id;
        this.video=video;
        this.voteAverage=voteAverage;
        this.title=title;
        this.popularity=popularity;
        this.posterPath=posterPath;
        this.originLanguage=originLanguage;
        this.originalTitle=originalTitle;
        this.genreIds=genreIds;
        this.backdroPath=backdroPath;
        this.adult=adult;
        this.overview=overview;
        this.releaseDate=releaseDate;
    }
    String baseImageUrl="https://image.tmdb.org/t/p/w500";

    public String getPosterPath(){
        return "https://image.tmdb.org/t/p/w500" +posterPath;
    }
    public void setPosterPath(String posterPath){
        this.posterPath=posterPath;
    }

    public boolean isAdult(){
        return this.adult;
    }
    public void setAdult(boolean adult){
        this.adult=adult;
    }

    public String getOverview(){
        return this.overview;
    }
    public void setOverview(String overview){
      this.overview=overview;
    }

    public String getReleaseDate(){
        return this.releaseDate;
    }
    public void setReleaseDate(String releaseDate){
        this.releaseDate=releaseDate;
    }

    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id=id;
    }

    public String getOriginalTitle(){
        return this.originalTitle;
    }
    public void setOriginalTitle(String originalTitle){
        this.originalTitle=originalTitle;
    }

    public float getVoteAverage(){
        return this.voteAverage;
    }
    public void setVoteAverage(float voteAverage){
        this.voteAverage=voteAverage;
    }

    public String getBackdroPath(){return "https://image.tmdb.org/t/p/w500"+ this.backdroPath;}
    public void setBackdroPath(String backdroPath){this.backdroPath=backdroPath;}
}
