package com.example.alihasan.synergytwo.Database.InUploadDatabase;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(tableName = "inupload_table", primaryKeys = {"caseNo", "caseType"})
public class InUplaod {

    @NonNull
    private String caseNo;

    @NonNull
    private String caseType;


    public InUplaod(@NonNull String caseNo,@NonNull String caseType) {
        this.caseNo = caseNo;
        this.caseType = caseType;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public String getCaseType() {
        return caseType;
    }
}
