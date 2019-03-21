package com.example.alihasan.synergytwo.Database.EmploymentDatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "employment_table")
public class Employment {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "employment")
    private String EMPLOYMENT;

    private String CASENO;
    private String ADDRESS;
    private String EASELOCATE;
    private String APPLCOMPANYNAME;
    private String LOCALITYTYPE;
    private String ADDRESSCONF;
    private String LANDMARK;
    private String APPLDESIGNATION;
    private String PERSONMET;
    private String PERSONDESIGNATION;
    private String PERSONCONTACT;
    private String DOESAPPLWORK;
    private String OFFICETEL;
    private String OFFICEBOARD;
    private String ORGANISATIONTYPE;
    private String DOJ;
    private String VISITINGCARD;
    private String NATUREBUSNIESS;
    private String JOBTYPE;
    private String WORKINGAS;
    private String JOBTRANSFER;
    private String NOSEMP;
    private String PERSONCONTACTED;
    private String PERSONCONDESIGNATION;
    private String MANAGER;
    private String MANAGERDESIGNATION;
    private String MANAGERCONTACT;
    private String SALARY;
    private String TPCCONFIRM;
    private String TPCNAME;
    private String OVERALLSTATUS;
    private String REASONNEGATIVEFI;
    private String LATITUDE;
    private String LONGITUDE;
    private String REMARKS;

    public Employment(@NonNull String EMPLOYMENT, String CASENO, String ADDRESS, String EASELOCATE, String APPLCOMPANYNAME, String LOCALITYTYPE, String ADDRESSCONF, String LANDMARK, String APPLDESIGNATION, String PERSONMET, String PERSONDESIGNATION, String PERSONCONTACT, String DOESAPPLWORK, String OFFICETEL, String OFFICEBOARD, String ORGANISATIONTYPE, String DOJ, String VISITINGCARD, String NATUREBUSNIESS, String JOBTYPE, String WORKINGAS, String JOBTRANSFER, String NOSEMP, String PERSONCONTACTED, String PERSONCONDESIGNATION, String MANAGER, String MANAGERDESIGNATION, String MANAGERCONTACT, String SALARY, String TPCCONFIRM, String TPCNAME, String OVERALLSTATUS, String REASONNEGATIVEFI, String LATITUDE, String LONGITUDE, String REMARKS) {
        this.EMPLOYMENT = EMPLOYMENT;
        this.CASENO = CASENO;
        this.ADDRESS = ADDRESS;
        this.EASELOCATE = EASELOCATE;
        this.APPLCOMPANYNAME = APPLCOMPANYNAME;
        this.LOCALITYTYPE = LOCALITYTYPE;
        this.ADDRESSCONF = ADDRESSCONF;
        this.LANDMARK = LANDMARK;
        this.APPLDESIGNATION = APPLDESIGNATION;
        this.PERSONMET = PERSONMET;
        this.PERSONDESIGNATION = PERSONDESIGNATION;
        this.PERSONCONTACT = PERSONCONTACT;
        this.DOESAPPLWORK = DOESAPPLWORK;
        this.OFFICETEL = OFFICETEL;
        this.OFFICEBOARD = OFFICEBOARD;
        this.ORGANISATIONTYPE = ORGANISATIONTYPE;
        this.DOJ = DOJ;
        this.VISITINGCARD = VISITINGCARD;
        this.NATUREBUSNIESS = NATUREBUSNIESS;
        this.JOBTYPE = JOBTYPE;
        this.WORKINGAS = WORKINGAS;
        this.JOBTRANSFER = JOBTRANSFER;
        this.NOSEMP = NOSEMP;
        this.PERSONCONTACTED = PERSONCONTACTED;
        this.PERSONCONDESIGNATION = PERSONCONDESIGNATION;
        this.MANAGER = MANAGER;
        this.MANAGERDESIGNATION = MANAGERDESIGNATION;
        this.MANAGERCONTACT = MANAGERCONTACT;
        this.SALARY = SALARY;
        this.TPCCONFIRM = TPCCONFIRM;
        this.TPCNAME = TPCNAME;
        this.OVERALLSTATUS = OVERALLSTATUS;
        this.REASONNEGATIVEFI = REASONNEGATIVEFI;
        this.LATITUDE = LATITUDE;
        this.LONGITUDE = LONGITUDE;
        this.REMARKS = REMARKS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getEMPLOYMENT() {
        return EMPLOYMENT;
    }

    public String getCASENO() {
        return CASENO;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public String getEASELOCATE() {
        return EASELOCATE;
    }

    public String getAPPLCOMPANYNAME() {
        return APPLCOMPANYNAME;
    }

    public String getLOCALITYTYPE() {
        return LOCALITYTYPE;
    }

    public String getADDRESSCONF() {
        return ADDRESSCONF;
    }

    public String getLANDMARK() {
        return LANDMARK;
    }

    public String getAPPLDESIGNATION() {
        return APPLDESIGNATION;
    }

    public String getPERSONMET() {
        return PERSONMET;
    }

    public String getPERSONDESIGNATION() {
        return PERSONDESIGNATION;
    }

    public String getPERSONCONTACT() {
        return PERSONCONTACT;
    }

    public String getDOESAPPLWORK() {
        return DOESAPPLWORK;
    }

    public String getOFFICETEL() {
        return OFFICETEL;
    }

    public String getOFFICEBOARD() {
        return OFFICEBOARD;
    }

    public String getORGANISATIONTYPE() {
        return ORGANISATIONTYPE;
    }

    public String getDOJ() {
        return DOJ;
    }

    public String getVISITINGCARD() {
        return VISITINGCARD;
    }

    public String getNATUREBUSNIESS() {
        return NATUREBUSNIESS;
    }

    public String getJOBTYPE() {
        return JOBTYPE;
    }

    public String getWORKINGAS() {
        return WORKINGAS;
    }

    public String getJOBTRANSFER() {
        return JOBTRANSFER;
    }

    public String getNOSEMP() {
        return NOSEMP;
    }

    public String getPERSONCONTACTED() {
        return PERSONCONTACTED;
    }

    public String getPERSONCONDESIGNATION() {
        return PERSONCONDESIGNATION;
    }

    public String getMANAGER() {
        return MANAGER;
    }

    public String getMANAGERDESIGNATION() {
        return MANAGERDESIGNATION;
    }

    public String getMANAGERCONTACT() {
        return MANAGERCONTACT;
    }

    public String getSALARY() {
        return SALARY;
    }

    public String getTPCCONFIRM() {
        return TPCCONFIRM;
    }

    public String getTPCNAME() {
        return TPCNAME;
    }

    public String getOVERALLSTATUS() {
        return OVERALLSTATUS;
    }

    public String getREASONNEGATIVEFI() {
        return REASONNEGATIVEFI;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public String getLONGITUDE() {
        return LONGITUDE;
    }

    public String getREMARKS() {
        return REMARKS;
    }
}
