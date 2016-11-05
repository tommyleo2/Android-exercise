package com.example.tommy.project_1;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tommy on 10/21/16.
 */

public class StartupActivity extends AppCompatActivity {
    // Storage Permissions variables
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //permission method.
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private MusicService musicService;
    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService = ((MusicService.MusicServiceBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
        }
    };

    class UIHandler extends Handler {
        private final int PROGRESS = 1;
        private final int ROTATE = 2;
        Thread progressThread, rotationThread;
        UIHandler() {
            progressThread = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        if (musicService != null && isPlaying) {
                            int progress = musicService.getProgress();
                            String time = dateFormat.format(new Date(progress));
                            Message msg = obtainMessage();
                            msg.obj = time;
                            msg.arg1 = progress;
                            msg.what = PROGRESS;
                            sendMessage(msg);
                        }
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            rotationThread = new Thread() {
                private float rotation = 0;
                @Override
                public void run() {
                    while (true) {
                        if (isPlaying) {
                            Message msg = obtainMessage();
                            rotation += 0.5;
                            msg.obj = rotation;
                            msg.what = ROTATE;
                            sendMessage(msg);
                        }
                        try {
                            sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
        }
        public void start() {
            progressThread.start();
            rotationThread.start();
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PROGRESS:
                    textViewPlayedTime.setText((String)msg.obj);
                    progressBar.setProgress(msg.arg1);
                    Log.v("Handler", "Progress: " + msg.obj);
                    break;
                case ROTATE:
                    cover.setRotation((float)msg.obj);
                    //Log.v("Handler", "Rotation: " + Float.toString((float)msg.obj));
                    break;
            }
        }
    }

    private boolean isPlaying = false;
    ImageView cover;
    Button buttonPlay, buttonStop, buttonQuit;
    TextView textViewStatus, textViewPlayedTime, textViewDuration, textViewMediaPath;
    SeekBar progressBar;

    UIHandler mHandler = new UIHandler();
    SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);

        verifyStoragePermissions(StartupActivity.this);

        cover = (ImageView)findViewById(R.id.album_cover);
        buttonPlay = (Button)findViewById(R.id.button_play);
        buttonStop = (Button)findViewById(R.id.button_stop);
        buttonQuit = (Button)findViewById(R.id.button_quit);
        textViewStatus = (TextView)findViewById(R.id.status);
        textViewPlayedTime = (TextView)findViewById(R.id.current_played_time);
        textViewDuration = (TextView)findViewById(R.id.music_length);
        progressBar = (SeekBar)findViewById(R.id.progress_bar);
        textViewMediaPath = (TextView)findViewById(R.id.media_path);

        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    musicService.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (!isPlaying) {
                    playMusic();
                }
            }
        });

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    pauseMusic();
                } else {
                    playMusic();
                }
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusic();
            }
        });

        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(sc);
                try {
                    StartupActivity.this.finish();
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, sc, BIND_AUTO_CREATE);

        mHandler.start();
    }

    public void playMusic() {
        musicService.play();
        buttonPlay.setText("PAUSE");
        textViewStatus.setText("Playing");
        isPlaying = true;
        textViewDuration.setText(dateFormat.format(musicService.getDuration()));
        progressBar.setMax(musicService.getDuration());
        textViewMediaPath.setText(musicService.getMediaPath());

    }
    public void pauseMusic() {
        musicService.pause();
        buttonPlay.setText("PLAY");
        textViewStatus.setText("Paused");
        isPlaying = false;
    }
    public void stopMusic() {
        musicService.stop();
        buttonPlay.setText("PLAY");
        textViewStatus.setText("Stopped");
        isPlaying = false;
    }
}
