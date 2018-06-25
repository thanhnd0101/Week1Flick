package com.example.admin.week1projectflick.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("youtube")
    private List<Youtube> youtubes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Youtube> getYoutubes() {
        return youtubes;
    }

    public void setYoutubes(List<Youtube> youtubes) {
        this.youtubes = youtubes;
    }
}
