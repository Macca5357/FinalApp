/* Student name: Gavin McCarthy
 * Student id: 19237766
 */
package com.seo.viedosapp.Models;

public class VideoModel {
    int videoID;
    String videoURL;
    String videoTitle;

    public int videoID() {
        return videoID;
    }

    public void setVideoID(int videoID) {
        this.videoID = videoID;
    }

    public String videoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String videoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }
}
