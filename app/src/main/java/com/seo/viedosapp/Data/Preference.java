/* Student name: Gavin McCarthy
 * Student id: 19237766
 */
package com.seo.viedosapp.Data;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;


public class Preference {
    Context context;
    public Preference(Context context) {
        this.context=context;
    }
    public void setVideoPlayTime(String videoID,int duration){
        SharedPreferences sh=context.getSharedPreferences(
                "appDataFile", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.putInt(videoID,duration);
        myEdit.apply();
    }
    public int getVideoDuration(String videoID){
        SharedPreferences sh=context.getSharedPreferences(
                "appDataFile", MODE_PRIVATE);
          if(!sh.contains(videoID)){
              SharedPreferences.Editor myEdit = sh.edit();
              myEdit.putInt(videoID, 0);
              myEdit.apply();
          }
        return sh.getInt(videoID,0);
    }
}
