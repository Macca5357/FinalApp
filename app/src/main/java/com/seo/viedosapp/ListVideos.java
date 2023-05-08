/* Student name: Gavin McCarthy
 * Student id: 19237766
 */
package com.seo.viedosapp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.seo.viedosapp.Adapters.VideoAdapter;
import com.seo.viedosapp.Database.Database;
import com.seo.viedosapp.Models.VideoModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListVideos extends AppCompatActivity {
     LinearLayout layoutEmpty;
     RecyclerView recyclerView;
     Button buttonAddNewVideos;
     Database database;
     List<VideoModel> videoModels;
     VideoAdapter videoAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_videos);
        database=new Database(this);
        initViews();
        initRecyclerView();
        getAllVideos();
    }

    private void getAllVideos() {
        videoModels.clear();
        videoModels.addAll(database.getVideos());
        if(videoModels.size()>0){
            layoutEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }else {
            layoutEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        videoAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        videoModels=new ArrayList<>();
        videoAdapter = new VideoAdapter(videoModels, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(videoAdapter);
        videoAdapter.setOnItemClickListener(new VideoAdapter.onItemClickListener() {
            @Override
            public void playVideo(int position) {
                Intent intent=new Intent(ListVideos.this,VideoPlayerActivity.class);
                intent.putExtra("videoURL",videoModels.get(position).videoURL());
                intent.putExtra("videoName",videoModels.get(position).videoTitle());
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        layoutEmpty=findViewById(R.id.layoutEmpty);
        recyclerView=findViewById(R.id.recyclerView);
        buttonAddNewVideos=findViewById(R.id.buttonAddNewVideos);
          buttonAddNewVideos.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  openDialogAddNewVideos();
              }
          });

    }

    private void openDialogAddNewVideos() {
        Dialog dialog=new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_add_video);
        dialog.setCancelable(false);
         dialog.show();
        EditText video_title_edit_text,video_url_edit_text;
        Button buttonCancel,button_submit;

        video_title_edit_text=dialog.findViewById(R.id.video_title_edit_text);
        video_url_edit_text=dialog.findViewById(R.id.video_url_edit_text);
        buttonCancel=dialog.findViewById(R.id. buttonCancel);
        button_submit=dialog.findViewById(R.id.submit_button);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoModel videoModel=new VideoModel();
                videoModel.setVideoTitle(video_title_edit_text.getText().toString());
                videoModel.setVideoURL(video_url_edit_text.getText().toString());
                if(videoModel.videoURL().equals("")){
                    Toast.makeText(ListVideos.this, "Video URL required", Toast.LENGTH_SHORT).show();
                }else if(videoModel.videoTitle().equals("")){
                    Toast.makeText(ListVideos.this, "Video Title required", Toast.LENGTH_SHORT).show();
                }else {
                    database.insertVideo(videoModel);
                    dialog.dismiss();
                    Toast.makeText(ListVideos.this, "Video Added successfully", Toast.LENGTH_SHORT).show();
                     getAllVideos();
                }
            }
        });



    }
}
