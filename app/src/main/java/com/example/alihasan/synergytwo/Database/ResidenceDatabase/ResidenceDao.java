package com.example.alihasan.synergytwo.Database.ResidenceDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.alihasan.synergytwo.Database.EmploymentDatabase.Employment;

import java.util.List;

@Dao
public interface ResidenceDao {

    @Insert
    void insert(Residence residence);

    @Delete
    void delete(Residence residence);

    @Query("SELECT count(*) FROM residence_table")
    int getCount();

    @Query("SELECT * from residence_table")
    List<Residence> getAllData();
}
