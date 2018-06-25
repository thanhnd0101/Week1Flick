package com.example.admin.week1projectflick.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("results")
    private List<Movie> results;

    public int getPage(){
        return this.page;
    }
    public void setPage(int page){
        this.page=page;
    }

    public int getTotalResults(){
        return this.totalResults;
    }
    public void setTotalResults(int totalResults){
        this.totalPages=totalResults;
    }

    public int getTotalPages(){
        return this.totalPages;
    }
    public void setTotalPages(int totalPages){
        this.totalPages=totalPages;
    }

    public List<Movie> getResults(){
        return this.results;
    }
    public void setResults(List<Movie> results){
        this.results=results;
    }
}
