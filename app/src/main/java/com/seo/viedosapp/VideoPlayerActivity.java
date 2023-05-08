/* Student name: Gavin McCarthy
 * Student id: 19237766
 */
package com.seo.viedosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoPlayerActivity extends AppCompatActivity {
    VideoView simpleVideoView;
    MediaController mediaControls;

    TextView video_title_text_view;
    String videoURL = "";
    String videoTitle="";
    ProgressBar progressBarVideoProgress;
    private BroadcastReceiver videoBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        videoBroadcastReceiver = new VideoBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("VIDEO_STARTED");
        filter.addAction("VIDEO_STOPPED");
        registerReceiver(videoBroadcastReceiver, filter);
        initView();
        getIntentData();
        playVideo();
    }

    private void playVideo() {
        if (mediaControls == null) {
            // create an object of media controller class
            mediaControls = new MediaController(VideoPlayerActivity.this);
            mediaControls.setAnchorView(simpleVideoView);
        }
        simpleVideoView.setMediaController(mediaControls);
        // set the uri for the video view
        // simpleVideoView.setVideoURI(Uri.parse(videoURL));
        simpleVideoView.setVideoPath(videoURL);
        // start a video
        simpleVideoView.start();

        // implement on completion listener on video view
        simpleVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressBarVideoProgress.setVisibility(View.GONE);
                Intent videoStartedIntent = new Intent("VIDEO_STARTED");
                videoStartedIntent.putExtra("videoTitle",videoTitle );
                sendBroadcast(videoStartedIntent);

            }
        });
        simpleVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(getApplicationContext(), "Thank you - video finished.", Toast.LENGTH_LONG).show(); // display a toast when a video is completed
                Intent videoStoppedIntent = new Intent("VIDEO_STOPPED");
                sendBroadcast(videoStoppedIntent);
            }
        });
        simpleVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Toast.makeText(getApplicationContext(), "Oops An Error Occur While Playing Video...!!!", Toast.LENGTH_LONG).show(); // display a toast when an error occurs while playing an video
                return false;
            }
        });

    }

    private void getIntentData() {
        Intent intent = getIntent();
        videoURL = intent.getStringExtra("videoURL");
        videoTitle=intent.getStringExtra("videoName");
        video_title_text_view.setText(videoTitle);
    }

    private void initView() {
        simpleVideoView = findViewById(R.id.video_view);
        progressBarVideoProgress = findViewById(R.id.loading_indicator);
        video_title_text_view=findViewById(R.id.video_title_text_view);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
       Intent videoStoppedIntent = new Intent("VIDEO_STOPPED");
       sendBroadcast(videoStoppedIntent);
        unregisterReceiver(videoBroadcastReceiver);
    }
}
