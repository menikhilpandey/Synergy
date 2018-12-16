package com.example.alihasan.synergytwo.api.model;

public class Debtor {

    private String caseNo;

    private String applName;

    private String address;

    private String altTele;

    private String typeCase;

//    private String productType;


    public Debtor(String caseNo, String applName, String address, String altTele, String typeCase) {
        this.caseNo = caseNo;
        this.applName = applName;
        this.address = address;
        this.altTele = altTele;
        this.typeCase = typeCase;
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
}
