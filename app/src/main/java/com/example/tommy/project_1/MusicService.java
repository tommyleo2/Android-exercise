package com.example.tommy.project_1;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

/**
 * Created by tommy on 11/5/16.
 */

public class MusicService extends Service {
    private String[] songs = {"K.Will-Melt.mp3", "5.mp3"};
    String srcPath;
    public final MusicServiceBinder musicServiceBinder = new MusicServiceBinder();
    private MediaPlayer mediaPlayer;
    private boolean isMediaLoaded = false;
    public class MusicServiceBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            Log.v("Service", "create service");
        }
        return musicServiceBinder;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    public int getProgress() {
        return mediaPlayer.getCurrentPosition();

    }

    public void setProgress(int progress) {
        mediaPlayer.seekTo(progress);
    }

    public void loadMedia(String song) {
        Log.v("Media", "load music");
        try {
            //String srcPath = Environment.getExternalStorageDirectory().getPath() + "/Music/5.mp3";
            srcPath = Environment.getExternalStorageDirectory().getPath() + "/Music/" + song;
            Log.v("Loading", srcPath);
            mediaPlayer.setDataSource(srcPath);
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
            isMediaLoaded = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMediaPath() {
        return srcPath;
    }

    public void play() {
        if (!isMediaLoaded) {
            loadMedia(songs[0]);
        }
        if (!mediaPlayer.isPlaying()) {
            Log.v("Media", "play music");
            mediaPlayer.start();
        }
    }

    public void pause() {
        if (mediaPlayer.isPlaying()) {
            Log.v("Media", "pause music");
            mediaPlayer.pause();
        }
    }

    public void stop() {
        Log.v("Media", "stop music");
        mediaPlayer.stop();
        try {
            mediaPlayer.prepare();
            mediaPlayer.seekTo(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
