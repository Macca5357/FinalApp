/* Student name: Gavin McCarthy
 * Student id: 19237766
 */
package com.seo.viedosapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.seo.viedosapp.Models.VideoModel;
import com.seo.viedosapp.R;

import java.util.List;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.CustomViewHolder> {
    List<VideoModel> videoModels;
    Context context;
    private  onItemClickListener mListener;
      public  interface onItemClickListener{
          void  playVideo(int position);

        }
     public  void setOnItemClickListener(onItemClickListener listener){//item click listener initialization
          mListener=listener;
     }
     public static class  CustomViewHolder extends RecyclerView.ViewHolder{
      TextView video_title_text_view;
      Button play_button;
          public CustomViewHolder(View itemView, final onItemClickListener listener) {
             super(itemView);
            video_title_text_view=itemView.findViewById(R.id.video_title_text_view);
              play_button=itemView.findViewById(R.id.play_button);
              play_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        int position=getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            listener.playVideo(position);
                        }
                    }
                }
            });
        }
    }
    public VideoAdapter(List<VideoModel> videoModels, Context context) {
        this.videoModels=videoModels;
        this.context = context;
    }
    @Override
    public int getItemViewType(int position) {
            return R.layout.video_item;
    }
    @Override
    public int getItemCount() {
        return  videoModels.size();
    }
    
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false),mListener);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
          holder.video_title_text_view.setText(videoModels.get(position).videoTitle());
      }
}
