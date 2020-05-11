package com.shailendra.photonotesgallery.api;

public class VideoEntry extends LocalMediaEntry {

    public VideoEntry() {
    }

    @Override
    public boolean isVideo() {
        return true;
    }
}
