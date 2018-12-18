package com.example.alihasan.synergytwo.api.model;

import com.google.gson.annotations.SerializedName;

public class BusinessModel {

//    private String applName;
//
//    private String address;
//
//    private String contactNo;

    private  String tableName;

    @SerializedName("CASENO")
    private String caseNo;

    @SerializedName("APPLCOMPANYNAME")
    private String compName;

    @SerializedName("NATUREBUSNIESS")
    private String businessNature;

    @SerializedName("APPLDESIGNATION")
    private String designation;

    @SerializedName("WORKINGSINCE")
    private String workingSince;

    @SerializedName("PERSONCONTACTED")
    private String personContacted;

    @SerializedName("PERSONDESIGNATION")
    private String desigPersonMet;

    @SerializedName("NOSEMP")
    private String empNo;

    @SerializedName("LANDMARK")
    private String landmark;

    @SerializedName("NOSBRANCHES")
    private String branchNo;

    @SerializedName("NOSYEARSATADDRESS")
    private String yearsPresentAdd;

    @SerializedName("CONTACTVERIF1")
    private String conVer1;

    @SerializedName("CONTACTVERIF2")
    private String conVer2;

    @SerializedName("PROOFDETAILS")
    private String addProofDetail;

//    private String pdaNoSpinner;

    @SerializedName("EASELOCATE")
    private String easeLocSpinner;

    @SerializedName("OFFICEOWNERSHIP")
    private String offOwnershipSpinner;

    @SerializedName("LOCALITYTYPE")
    private String localityTypeSpinner;

    @SerializedName("BUSINESSSETUP")
    private String businessSetupSpinner;

    @SerializedName("BUSINESSBOARD")
    private String businessBoardSpinner;

    @SerializedName("VISITINGCARD")
    private String visCardSpinner;

    @SerializedName("APPLNAMEVERIFFROM")
    private String applVeriFrom;

    @SerializedName("CONTACTFEEDBACK")
    private String conVeriFeed;

    @SerializedName("POLITICALLINK")
    private String polLinkSpinner;

    @SerializedName("OVERALLSTATUS")
    private String overallStatusSpinner;

    @SerializedName("REASONNEGATIVEFI")
    private String reasonNegativeSpinner;

    @SerializedName("LATITUDE")
    private String lattitude;

    @SerializedName("LONGITUDE")
    private String longitude;

    public BusinessModel(String tableName,
                         String caseNo, String compName,
                         String businessNature, String designation, String workingSince, String personContacted, String desigPersonMet,
                         String empNo, String landmark, String branchNo, String yearsPresentAdd, String conVer1, String conVer2,
                         String addProofDetail, String easeLocSpinner, String offOwnershipSpinner,
                         String localityTypeSpinner, String businessSetupSpinner, String businessBoardSpinner, String visCardSpinner,
                         String applVeriFrom, String conVeriFeed, String polLinkSpinner, String overallStatusSpinner,
                         String reasonNegativeSpinner, String lattitude, String longitude) {
        this.tableName = tableName;
        this.caseNo = caseNo;
//        this.applName = applName;
//        this.address = address;
//        this.contactNo = contactNo;
        this.compName = compName;
        this.businessNature = businessNature;
        this.designation = designation;
        this.workingSince = workingSince;
        this.personContacted = personContacted;
        this.desigPersonMet = desigPersonMet;
        this.empNo = empNo;
        this.landmark = landmark;
        this.branchNo = branchNo;
        this.yearsPresentAdd = yearsPresentAdd;
        this.conVer1 = conVer1;
        this.conVer2 = conVer2;
        this.addProofDetail = addProofDetail;
//        this.pdaNoSpinner = pdaNoSpinner;
        this.easeLocSpinner = easeLocSpinner;
        this.offOwnershipSpinner = offOwnershipSpinner;
        this.localityTypeSpinner = localityTypeSpinner;
        this.businessSetupSpinner = businessSetupSpinner;
        this.businessBoardSpinner = businessBoardSpinner;
        this.visCardSpinner = visCardSpinner;
        this.applVeriFrom = applVeriFrom;
        this.conVeriFeed = conVeriFeed;
        this.polLinkSpinner = polLinkSpinner;
        this.overallStatusSpinner = overallStatusSpinner;
        this.reasonNegativeSpinner = reasonNegativeSpinner;
        this.lattitude = lattitude;
        this.longitude = longitude;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

//    public String getApplName() {
//        return applName;
//    }
//
//    public void setApplName(String applName) {
//        this.applName = applName;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getContactNo() {
//        return contactNo;
//    }
//
//    public void setContactNo(String contactNo) {
//        this.contactNo = contactNo;
//    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getBusinessNature() {
        return businessNature;
    }

    public void setBusinessNature(String businessNature) {
        this.businessNature = businessNature;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getWorkingSince() {
        return workingSince;
    }

    public void setWorkingSince(String workingSince) {
        this.workingSince = workingSince;
    }

    public String getPersonContacted() {
        return personContacted;
    }

    public void setPersonContacted(String personContacted) {
        this.personContacted = personContacted;
    }

    public String getDesigPersonMet() {
        return desigPersonMet;
    }

    public void setDesigPersonMet(String desigPersonMet) {
        this.desigPersonMet = desigPersonMet;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getYearsPresentAdd() {
        return yearsPresentAdd;
    }

    public void setYearsPresentAdd(String yearsPresentAdd) {
        this.yearsPresentAdd = yearsPresentAdd;
    }

    public String getConVer1() {
        return conVer1;
    }

    public void setConVer1(String conVer1) {
        this.conVer1 = conVer1;
    }

    public String getConVer2() {
        return conVer2;
    }

    public void setConVer2(String conVer2) {
        this.conVer2 = conVer2;
    }

    public String getAddProofDetail() {
        return addProofDetail;
    }

    public void setAddProofDetail(String addProofDetail) {
        this.addProofDetail = addProofDetail;
    }

//    public String getPdaNoSpinner() {
//        return pdaNoSpinner;
//    }
//
//    public void setPdaNoSpinner(String pdaNoSpinner) {
//        this.pdaNoSpinner = pdaNoSpinner;
//    }

    public String getEaseLocSpinner() {
        return easeLocSpinner;
    }

    public void setEaseLocSpinner(String easeLocSpinner) {
        this.easeLocSpinner = easeLocSpinner;
    }

    public String getOffOwnershipSpinner() {
        return offOwnershipSpinner;
    }

    public void setOffOwnershipSpinner(String offOwnershipSpinner) {
        this.offOwnershipSpinner = offOwnershipSpinner;
    }

    public String getLocalityTypeSpinner() {
        return localityTypeSpinner;
    }

    public void setLocalityTypeSpinner(String localityTypeSpinner) {
        this.localityTypeSpinner = localityTypeSpinner;
    }

    public String getBusinessSetupSpinner() {
        return businessSetupSpinner;
    }

    public void setBusinessSetupSpinner(String businessSetupSpinner) {
        this.businessSetupSpinner = businessSetupSpinner;
    }

    public String getBusinessBoardSpinner() {
        return businessBoardSpinner;
    }

    public void setBusinessBoardSpinner(String businessBoardSpinner) {
        this.businessBoardSpinner = businessBoardSpinner;
    }

    public String getVisCardSpinner() {
        return visCardSpinner;
    }

    public void setVisCardSpinner(String visCardSpinner) {
        this.visCardSpinner = visCardSpinner;
    }

    public String getApplVeriFrom() {
        return applVeriFrom;
    }

    public void setApplVeriFrom(String applVeriFrom) {
        this.applVeriFrom = applVeriFrom;
    }

    public String getConVeriFeed() {
        return conVeriFeed;
    }

    public void setConVeriFeed(String conVeriFeed) {
        this.conVeriFeed = conVeriFeed;
    }

    public String getPolLinkSpinner() {
        return polLinkSpinner;
    }

    public void setPolLinkSpinner(String polLinkSpinner) {
        this.polLinkSpinner = polLinkSpinner;
    }

    public String getOverallStatusSpinner() {
        return overallStatusSpinner;
    }

    public void setOverallStatusSpinner(String overallStatusSpinner) {
        this.overallStatusSpinner = overallStatusSpinner;
    }

    public String getReasonNegativeSpinner() {
        return reasonNegativeSpinner;
    }

    public void setReasonNegativeSpinner(String reasonNegativeSpinner) {
        this.reasonNegativeSpinner = reasonNegativeSpinner;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}

/*
public BusinessModel(String caseNo, String applName, String address, String contactNo, String compName,
                         String businessNature, String designation, String workingSince, String personContacted, String desigPersonMet,
                         String empNo, String landmark, String branchNo, String yearsPresentAdd, String conVer1, String conVer2,
                         String addProofDetail, String pdaNoSpinner, String easeLocSpinner, String offOwnershipSpinner,
                         String localityTypeSpinner, String businessSetupSpinner, String businessBoardSpinner, String visCardSpinner,
                         String applVeriFrom, String conVeriFeed, String polLinkSpinner, String overallStatusSpinner,
                         String reasonNegativeSpinner, String lattitude, String longitude) {
 */
