package com.example.alihasan.synergytwo.Database.ImageDatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "image_table")
public class ImageParam {

    private String encodedImage;

    @PrimaryKey
    @NonNull
    private String globalImageFileName;

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

    public String getEncodedImage() {
        return encodedImage;
    }

    @NonNull
    public String getGlobalImageFileName() {
        return globalImageFileName;
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
