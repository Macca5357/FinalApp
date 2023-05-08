/* Student name: Gavin McCarthy
 * Student id: 19237766
 */
package com.seo.viedosapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.seo.viedosapp.Models.VideoModel;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="appDatabase.db";
    private static final int DATABASE_Version = 1;
    private static final String VIDEO_TABLE="videos_table";
    private static final String VIDEO_ID="video_id";
    private static final String VIDEO_URL="video_url";
    private static final String VIDEO_TITLE="video_title";
    public String CREATE_VIDEO_TABLE = "CREATE TABLE " + VIDEO_TABLE + "("
            + VIDEO_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, " //index 0
            + VIDEO_URL + "  TEXT, " //index 1
            + VIDEO_TITLE+ " TEXT )";     //index 2

    private static final String DROP_VIDEO_TABLE ="DROP TABLE IF EXISTS "+VIDEO_TABLE;
    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_Version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_VIDEO_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_VIDEO_TABLE);
        db.execSQL(CREATE_VIDEO_TABLE);
        onCreate(db);
    }
    public boolean insertVideo(VideoModel videoModel){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(VIDEO_URL,videoModel.videoURL());
        contentValues.put(VIDEO_TITLE,videoModel.videoTitle());

        long result=db.insert(VIDEO_TABLE,null,contentValues);
        db.close();
        return result != -1;
    }

    public List<VideoModel> getVideos(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(" select * from "+VIDEO_TABLE,null );
        List<VideoModel> videoModels=new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                VideoModel videoModel=new VideoModel();
                videoModel.setVideoID(cursor.getInt(0));
                videoModel.setVideoURL(cursor.getString(1));
                videoModel.setVideoTitle(cursor.getString(2));
                videoModels.add(videoModel);
            }while (cursor.moveToNext());
        }
        return videoModels;
    }
}