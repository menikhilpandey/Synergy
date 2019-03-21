package com.example.alihasan.synergytwo.Database.ImageDatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "image_table")
public class ImageParam {

    @PrimaryKey
    @NonNull
    private String globalImageFileName;

    private String encodedImage;

    private String stringCaseNo;

    private String ACTIVITY;

    private String userName;

    public ImageParam( String encodedImage, @NonNull String globalImageFileName, String stringCaseNo, String ACTIVITY, String userName) {
        this.globalImageFileName = globalImageFileName;
        this.encodedImage = encodedImage;
        this.stringCaseNo = stringCaseNo;
        this.ACTIVITY = ACTIVITY;
        this.userName = userName;
    }

    @NonNull
    public String getGlobalImageFileName() {
        return globalImageFileName;
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public String getStringCaseNo() {
        return stringCaseNo;
    }

    public String getACTIVITY() {
        return ACTIVITY;
    }

    public String getUserName() {
        return userName;
    }
}
