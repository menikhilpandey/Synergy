package com.example.alihasan.synergytwo.api.model;

import com.google.gson.annotations.SerializedName;

public class EmploymentModel {

    private  String tableName;

    private String caseNo;

    /**
     * EDITTEXTS
     */

    String companyName;
    String landmark;
    String designOfApp;
    String personMet;
    String designOfPersonMet;
    String personContactNo;
    String officeTele;
    String dateOfJoin;
    String noOfEmp;
    String personContacted;
    String designOfPersonMet1;
    String nameOfRepManager;
    String contactNoOfRepoManager;
    String salary;
    String tpcPersonName;

    /**
     * SPINNER
     */

    String easeLocSpinner;
    String locTypeSpinner;
    String addConfirmSpinner;
    String doesAppWorkSpinner;
    String officeNameBoardSpinner;
    String orgTypeSpinner;
    String visitingCardObtSpinner;
    String NatureBusinessSpinner;
    String jobTypeSpinner;
    String workingAsSpinner;
    String jobTransferSpinner;
    String tpcConfirmSpinner;
    String overallStatusSpinner;
    String reasonNegativeSpinner;

    private String lattitude;

    private String longitude;

    public EmploymentModel(String tableName, String caseNo, String companyName, String landmark, String designOfApp, String personMet, String designOfPersonMet, String personContactNo, String officeTele, String dateOfJoin, String noOfEmp, String personContacted, String designOfPersonMet1, String nameOfRepManager, String contactNoOfRepoManager, String salary, String tpcPersonName, String easeLocSpinner, String locTypeSpinner, String addConfirmSpinner, String doesAppWorkSpinner, String officeNameBoardSpinner, String orgTypeSpinner, String visitingCardObtSpinner, String natureBusinessSpinner, String jobTypeSpinner, String workingAsSpinner, String jobTransferSpinner, String tpcConfirmSpinner, String overallStatusSpinner, String reasonNegativeSpinner, String lattitude, String longitude) {
        this.tableName = tableName;
        this.caseNo = caseNo;
        this.companyName = companyName;
        this.landmark = landmark;
        this.designOfApp = designOfApp;
        this.personMet = personMet;
        this.designOfPersonMet = designOfPersonMet;
        this.personContactNo = personContactNo;
        this.officeTele = officeTele;
        this.dateOfJoin = dateOfJoin;
        this.noOfEmp = noOfEmp;
        this.personContacted = personContacted;
        this.designOfPersonMet1 = designOfPersonMet1;
        this.nameOfRepManager = nameOfRepManager;
        this.contactNoOfRepoManager = contactNoOfRepoManager;
        this.salary = salary;
        this.tpcPersonName = tpcPersonName;
        this.easeLocSpinner = easeLocSpinner;
        this.locTypeSpinner = locTypeSpinner;
        this.addConfirmSpinner = addConfirmSpinner;
        this.doesAppWorkSpinner = doesAppWorkSpinner;
        this.officeNameBoardSpinner = officeNameBoardSpinner;
        this.orgTypeSpinner = orgTypeSpinner;
        this.visitingCardObtSpinner = visitingCardObtSpinner;
        NatureBusinessSpinner = natureBusinessSpinner;
        this.jobTypeSpinner = jobTypeSpinner;
        this.workingAsSpinner = workingAsSpinner;
        this.jobTransferSpinner = jobTransferSpinner;
        this.tpcConfirmSpinner = tpcConfirmSpinner;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getDesignOfApp() {
        return designOfApp;
    }

    public void setDesignOfApp(String designOfApp) {
        this.designOfApp = designOfApp;
    }

    public String getPersonMet() {
        return personMet;
    }

    public void setPersonMet(String personMet) {
        this.personMet = personMet;
    }

    public String getDesignOfPersonMet() {
        return designOfPersonMet;
    }

    public void setDesignOfPersonMet(String designOfPersonMet) {
        this.designOfPersonMet = designOfPersonMet;
    }

    public String getPersonContactNo() {
        return personContactNo;
    }

    public void setPersonContactNo(String personContactNo) {
        this.personContactNo = personContactNo;
    }

    public String getOfficeTele() {
        return officeTele;
    }

    public void setOfficeTele(String officeTele) {
        this.officeTele = officeTele;
    }

    public String getDateOfJoin() {
        return dateOfJoin;
    }

    public void setDateOfJoin(String dateOfJoin) {
        this.dateOfJoin = dateOfJoin;
    }

    public String getNoOfEmp() {
        return noOfEmp;
    }

    public void setNoOfEmp(String noOfEmp) {
        this.noOfEmp = noOfEmp;
    }

    public String getPersonContacted() {
        return personContacted;
    }

    public void setPersonContacted(String personContacted) {
        this.personContacted = personContacted;
    }

    public String getDesignOfPersonMet1() {
        return designOfPersonMet1;
    }

    public void setDesignOfPersonMet1(String designOfPersonMet1) {
        this.designOfPersonMet1 = designOfPersonMet1;
    }

    public String getNameOfRepManager() {
        return nameOfRepManager;
    }

    public void setNameOfRepManager(String nameOfRepManager) {
        this.nameOfRepManager = nameOfRepManager;
    }

    public String getContactNoOfRepoManager() {
        return contactNoOfRepoManager;
    }

    public void setContactNoOfRepoManager(String contactNoOfRepoManager) {
        this.contactNoOfRepoManager = contactNoOfRepoManager;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getTpcPersonName() {
        return tpcPersonName;
    }

    public void setTpcPersonName(String tpcPersonName) {
        this.tpcPersonName = tpcPersonName;
    }

    public String getEaseLocSpinner() {
        return easeLocSpinner;
    }

    public void setEaseLocSpinner(String easeLocSpinner) {
        this.easeLocSpinner = easeLocSpinner;
    }

    public String getLocTypeSpinner() {
        return locTypeSpinner;
    }

    public void setLocTypeSpinner(String locTypeSpinner) {
        this.locTypeSpinner = locTypeSpinner;
    }

    public String getAddConfirmSpinner() {
        return addConfirmSpinner;
    }

    public void setAddConfirmSpinner(String addConfirmSpinner) {
        this.addConfirmSpinner = addConfirmSpinner;
    }

    public String getDoesAppWorkSpinner() {
        return doesAppWorkSpinner;
    }

    public void setDoesAppWorkSpinner(String doesAppWorkSpinner) {
        this.doesAppWorkSpinner = doesAppWorkSpinner;
    }

    public String getOfficeNameBoardSpinner() {
        return officeNameBoardSpinner;
    }

    public void setOfficeNameBoardSpinner(String officeNameBoardSpinner) {
        this.officeNameBoardSpinner = officeNameBoardSpinner;
    }

    public String getOrgTypeSpinner() {
        return orgTypeSpinner;
    }

    public void setOrgTypeSpinner(String orgTypeSpinner) {
        this.orgTypeSpinner = orgTypeSpinner;
    }

    public String getVisitingCardObtSpinner() {
        return visitingCardObtSpinner;
    }

    public void setVisitingCardObtSpinner(String visitingCardObtSpinner) {
        this.visitingCardObtSpinner = visitingCardObtSpinner;
    }

    public String getNatureBusinessSpinner() {
        return NatureBusinessSpinner;
    }

    public void setNatureBusinessSpinner(String natureBusinessSpinner) {
        NatureBusinessSpinner = natureBusinessSpinner;
    }

    public String getJobTypeSpinner() {
        return jobTypeSpinner;
    }

    public void setJobTypeSpinner(String jobTypeSpinner) {
        this.jobTypeSpinner = jobTypeSpinner;
    }

    public String getWorkingAsSpinner() {
        return workingAsSpinner;
    }

    public void setWorkingAsSpinner(String workingAsSpinner) {
        this.workingAsSpinner = workingAsSpinner;
    }

    public String getJobTransferSpinner() {
        return jobTransferSpinner;
    }

    public void setJobTransferSpinner(String jobTransferSpinner) {
        this.jobTransferSpinner = jobTransferSpinner;
    }

    public String getTpcConfirmSpinner() {
        return tpcConfirmSpinner;
    }

    public void setTpcConfirmSpinner(String tpcConfirmSpinner) {
        this.tpcConfirmSpinner = tpcConfirmSpinner;
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
