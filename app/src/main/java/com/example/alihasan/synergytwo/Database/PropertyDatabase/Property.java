package com.example.alihasan.synergytwo.Database.PropertyDatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "property_table")
public class Property {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "property")
    private String PROPERTY;

    private String CASENO;
    private String ADDRESS;
    private String EASELOCATE;
    private String PERSONCONTACTED;
    private String RELATIONSHIP;
    private String PROPERTYTYPE;
    private String NOSYEARSOWNER;
    private String AREA;
    private String DOCSVERIF;
    private String NEIGHBOURNAME1;
    private String ADDRESS1;
    private String NEIGHBOURNAME2;
    private String ADDRESS2;
    private String PROPERTYSOLDTO;
    private String PURCHASERDETAILS;
    private String APPLCOOPERATIVE;
    private String NEIGHBOURFEEDBACK;
    private String LOCALITYTYPE;
    private String AMBIENCE;
    private String APPLSTAYATADDRESS;
    private String VEHICLESEEN;
    private String POLITICALLINK;
    private String OVERALLSTATUS;
    private String REASONNEGATIVEFI;
    private String LATITUDE;
    private String LONGITUDE;
    private String REMARKS;

    public Property(@NonNull String PROPERTY, String CASENO, String ADDRESS, String EASELOCATE, String PERSONCONTACTED, String RELATIONSHIP, String PROPERTYTYPE, String NOSYEARSOWNER, String AREA, String DOCSVERIF, String NEIGHBOURNAME1, String ADDRESS1, String NEIGHBOURNAME2, String ADDRESS2, String PROPERTYSOLDTO, String PURCHASERDETAILS, String APPLCOOPERATIVE, String NEIGHBOURFEEDBACK, String LOCALITYTYPE, String AMBIENCE, String APPLSTAYATADDRESS, String VEHICLESEEN, String POLITICALLINK, String OVERALLSTATUS, String REASONNEGATIVEFI, String LATITUDE, String LONGITUDE, String REMARKS) {
        this.PROPERTY = PROPERTY;
        this.CASENO = CASENO;
        this.ADDRESS = ADDRESS;
        this.EASELOCATE = EASELOCATE;
        this.PERSONCONTACTED = PERSONCONTACTED;
        this.RELATIONSHIP = RELATIONSHIP;
        this.PROPERTYTYPE = PROPERTYTYPE;
        this.NOSYEARSOWNER = NOSYEARSOWNER;
        this.AREA = AREA;
        this.DOCSVERIF = DOCSVERIF;
        this.NEIGHBOURNAME1 = NEIGHBOURNAME1;
        this.ADDRESS1 = ADDRESS1;
        this.NEIGHBOURNAME2 = NEIGHBOURNAME2;
        this.ADDRESS2 = ADDRESS2;
        this.PROPERTYSOLDTO = PROPERTYSOLDTO;
        this.PURCHASERDETAILS = PURCHASERDETAILS;
        this.APPLCOOPERATIVE = APPLCOOPERATIVE;
        this.NEIGHBOURFEEDBACK = NEIGHBOURFEEDBACK;
        this.LOCALITYTYPE = LOCALITYTYPE;
        this.AMBIENCE = AMBIENCE;
        this.APPLSTAYATADDRESS = APPLSTAYATADDRESS;
        this.VEHICLESEEN = VEHICLESEEN;
        this.POLITICALLINK = POLITICALLINK;
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
    public String getPROPERTY() {
        return PROPERTY;
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

    public String getPERSONCONTACTED() {
        return PERSONCONTACTED;
    }

    public String getRELATIONSHIP() {
        return RELATIONSHIP;
    }

    public String getPROPERTYTYPE() {
        return PROPERTYTYPE;
    }

    public String getNOSYEARSOWNER() {
        return NOSYEARSOWNER;
    }

    public String getAREA() {
        return AREA;
    }

    public String getDOCSVERIF() {
        return DOCSVERIF;
    }

    public String getNEIGHBOURNAME1() {
        return NEIGHBOURNAME1;
    }

    public String getADDRESS1() {
        return ADDRESS1;
    }

    public String getNEIGHBOURNAME2() {
        return NEIGHBOURNAME2;
    }

    public String getADDRESS2() {
        return ADDRESS2;
    }

    public String getPROPERTYSOLDTO() {
        return PROPERTYSOLDTO;
    }

    public String getPURCHASERDETAILS() {
        return PURCHASERDETAILS;
    }

    public String getAPPLCOOPERATIVE() {
        return APPLCOOPERATIVE;
    }

    public String getNEIGHBOURFEEDBACK() {
        return NEIGHBOURFEEDBACK;
    }

    public String getLOCALITYTYPE() {
        return LOCALITYTYPE;
    }

    public String getAMBIENCE() {
        return AMBIENCE;
    }

    public String getAPPLSTAYATADDRESS() {
        return APPLSTAYATADDRESS;
    }

    public String getVEHICLESEEN() {
        return VEHICLESEEN;
    }

    public String getPOLITICALLINK() {
        return POLITICALLINK;
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
