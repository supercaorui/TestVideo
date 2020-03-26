package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private VideoView videoView;

    private List<Uri> pathList = new ArrayList<>();
    private MediaController mediaController;
    private int currentVideo;
    private boolean isPaused;
    private int currentPostion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
    }

    private void bindView() {
        videoView = findViewById(R.id.video_view);
//        mediaController = new MediaController(this);
        // todo 设置地址
        pathList.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.test3));
        pathList.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.test4));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
//                Toast.makeText(MainActivity.this, "准备中",Toast.LENGTH_SHORT).show();
                mp.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                    @Override
                    public void onSeekComplete(MediaPlayer mp) {
                        if (isPaused) {
                            videoView.start();
                            isPaused = false;
                        }
                    }
                });
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                nextVideo();
            }
        });
        videoView.setVideoURI(pathList.get(currentVideo));
//        videoView.setMediaController(mediaController);
        videoView.start();
    }

    private void nextVideo() {
        currentVideo++;
        if (currentVideo < pathList.size()) {
            videoView.setVideoURI(pathList.get(currentVideo));
//            videoView.setMediaController(mediaController);
            videoView.start();
        } else {
            Toast.makeText(MainActivity.this, "播放结束",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPaused = true;
        videoView.pause();
        currentPostion = videoView.getCurrentPosition();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPaused) {
            videoView.start();
            videoView.seekTo(currentPostion);

        }
//        isPaused = false;
    }
}
