package com.example.android.miwok;

public class Word {
    private static final int no_file_provided = -1;
    private int defaultText;
    private int miwokText;
    private int imageResourceId;
    private int audioResourceId;

    public Word(int defaultTrans, int miwokTrans, int AudioResId) {
        defaultText = defaultTrans;
        miwokText = miwokTrans;
        imageResourceId = no_file_provided;
        audioResourceId = AudioResId;
    }

    public Word(int defaultTrans, int miwokTrans, int ImageResId, int AudioResId) {
        defaultText = defaultTrans;
        miwokText = miwokTrans;
        imageResourceId = ImageResId;
        audioResourceId = AudioResId;
    }

    public int getDefaultText() {
        return defaultText;
    }

    public int getMiwokText() {
        return miwokText;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public int getAudioResourceId() {
        return audioResourceId;
    }

    public boolean hasImage() {
        return imageResourceId != no_file_provided;
    }
}
