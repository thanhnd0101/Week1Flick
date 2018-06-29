package com.example.admin.week1projectflick.adapter;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.admin.week1projectflick.R;
import com.example.admin.week1projectflick.model.Youtube;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

public class TrailerViewHolder extends RecyclerView.ViewHolder {

    private final String MyYouTubeApiKey = "AIzaSyDKjSlBvJyQwGKpmSjq8d7k0QZ-u7fpThM";
    private FragmentManager mFragmentManager;
    Boolean popularVideo;

    public TrailerViewHolder(@NonNull View itemView,Boolean popularVideo) {
        super(itemView);
        this.popularVideo=popularVideo;
    }


    public void bind(final Youtube trailer, Activity activity) {
        YouTubePlayerFragment youTubePlayerFragment = (YouTubePlayerFragment)
                activity.getFragmentManager().findFragmentById(R.id.youtubeFragment);


        youTubePlayerFragment.initialize(MyYouTubeApiKey, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    youTubePlayer.cueVideo(trailer.getSource());
                    if (popularVideo) {
                        popularVideo = false;
                        youTubePlayer.setFullscreen(true);
                        youTubePlayer.play();
                    }
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

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        });
    }

}
