package com.example.alihasan.synergytwo.Database.InUploadDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface InUploadDao {

    @Insert
    void insert(InUplaod inUpload);

    @Delete
    void delete(InUplaod inUplaod);

    @Query("SELECT * FROM inupload_table")
    List<InUplaod> getAllData();
}
