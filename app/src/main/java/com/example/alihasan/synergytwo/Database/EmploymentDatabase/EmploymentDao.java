package com.example.alihasan.synergytwo.Database.EmploymentDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.alihasan.synergytwo.Database.Business;

import java.util.List;

@Dao
public interface EmploymentDao {

    @Insert
    void insert(Employment employment);

    @Delete
    void delete(Employment employment);

    @Query("SELECT count(*) FROM employment_table")
    int getCount();

    @Query("SELECT * from employment_table")
    List<Employment> getAllData();
}
