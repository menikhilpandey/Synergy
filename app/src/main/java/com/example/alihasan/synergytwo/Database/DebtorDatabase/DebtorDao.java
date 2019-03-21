package com.example.alihasan.synergytwo.Database.DebtorDatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.alihasan.synergytwo.api.model.Debtor;

import java.util.List;

@Dao
public interface DebtorDao {

    @Insert
    void insert(Debtor debtor);

    @Delete
    void delete(Debtor debtor);

    @Query("DELETE FROM debtor_table")
    void deleteAll();

    @Query("SELECT * FROM debtor_table")
    LiveData<List<Debtor>> getAllDebtor();

    @Query("UPDATE debtor_table SET inUpload = 'true' WHERE typeCase = :typeCase AND caseNo = :caseNo")
    void updateInUpload(String typeCase, String caseNo);

    @Query("SELECT inUpload FROM debtor_table WHERE typeCase = :typeCase AND caseNo = :caseNo")
    String fetchInUpload(String typeCase, String caseNo);
}
