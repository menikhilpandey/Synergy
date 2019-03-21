package com.example.alihasan.synergytwo.api.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "debtor_table", primaryKeys = {"caseNo", "typeCase"})
public class Debtor {

    @NonNull
    @SerializedName("CASENO")
    private String caseNo;

    @SerializedName("APPLNAME")
    private String applName;

    @SerializedName("ADDRESS")
    private String address;

    @SerializedName("PHONE")
    private String altTele;

    @NonNull
    @SerializedName("CASETYPE")
    private String typeCase;

        @SerializedName("CLIENTCODE")
    private String clientCode;

//    private String productType;


    public Debtor(@NonNull String caseNo, String applName, String address, String altTele, String typeCase,String clientCode) {
        this.caseNo = caseNo;
        this.applName = applName;
        this.address = address;
        this.altTele = altTele;
        this.typeCase = typeCase;
        this.clientCode = clientCode;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getApplName() {
        return applName;
    }

    public void setApplName(String applName) {
        this.applName = applName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAltTele() {
        return altTele;
    }

    public void setAltTele(String altTele) {
        this.altTele = altTele;
    }

    public String getTypeCase() {
        return typeCase;
    }

    public void setTypeCase(String typeCase) {
        this.typeCase = typeCase;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }
}
