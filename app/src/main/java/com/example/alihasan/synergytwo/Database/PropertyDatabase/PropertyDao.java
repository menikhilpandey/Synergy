package com.example.alihasan.synergytwo.Database.PropertyDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.alihasan.synergytwo.Database.ResidenceDatabase.Residence;

import java.util.List;

@Dao
public interface PropertyDao {

    @Insert
    void insert(Property property);

    @Delete
    void delete(Property property);

    @Query("SELECT count(*) FROM property_table")
    int getCount();

    @Query("SELECT * from property_table")
    List<Property> getAllData();

}
