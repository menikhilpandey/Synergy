package com.example.alihasan.synergytwo.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BusinessDao {

    @Insert
    void insert(Business business);

    @Update
    void update(Business business);

    @Query("DELETE FROM business_table WHERE id = (SELECT MIN(id) FROM business_table)")
    void delete();

    @Query("DELETE FROM business_table")
    void deleteAll();

    @Query("SELECT count(*) FROM business_table")
    int getCount();

    @Query("SELECT * from business_table")
    List<Business> testGetAllWords();
}
