package com.example.alihasan.synergytwo.Database.ImageDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.alihasan.synergytwo.Database.Business;

import java.util.List;

@Dao
public interface ImageDao {

    @Insert
    void insert(ImageParam imageParam);

    @Delete
    void delete(ImageParam imageParam);

    @Query("SELECT count(*) FROM image_table")
    int getCount();

    @Query("SELECT * from image_table")
    List<ImageParam> getAllData();

}
