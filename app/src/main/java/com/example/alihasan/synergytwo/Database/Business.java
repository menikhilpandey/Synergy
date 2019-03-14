package com.example.alihasan.synergytwo.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "business_table")
public class Business {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "business")
    private String mBusiness;
    private final String mCASENO;
    private final String mADDRESS;
    private final String mEASELOCATE;
    private final String mOFFICEOWNERSHIP;
    private final String mAPPLCOMPANYNAME;
    private final String mLOCALITYTYPE;
    private final String mNATUREBUSNIESS;
    private final String mAPPLDESIGNATION;
    private final String mWORKINGSINCE;
    private final String mPERSONCONTACTED;
    private final String mPERSONDESIGNATION;
    private final String mNOSEMP;
    private final String mLANDMARK;
    private final String mNOSBRANCHES;
    private final String mBUSINESSSETUP;
    private final String mBUSINESSBOARD;
    private final String mNOSYEARSATADDRESS;
    private final String mVISITINGCARD;
    private final String mAPPLNAMEVERIFFROM;
    private final String mCONTACTVERIF1;
    private final String mCONTACTVERIF2;
    private final String mCONTACTFEEDBACK;
    private final String mPROOFDETAILS;
    private final String mPOLITICALLINK;
    private final String mOVERALLSTATUS;
    private final String mREASONNEGATIVEFI;
    private final String mLATITUDE;
    private final String mLONGITUDE;
    private final String mREMARKS;

    public Business(@NonNull String mBusiness,
                    String mCASENO,
                    String mADDRESS,
                    String mEASELOCATE,
                    String mOFFICEOWNERSHIP,
                    String mAPPLCOMPANYNAME,
                    String mLOCALITYTYPE,
                    String mNATUREBUSNIESS,
                    String mAPPLDESIGNATION,
                    String mWORKINGSINCE,
                    String mPERSONCONTACTED,
                    String mPERSONDESIGNATION,
                    String mNOSEMP,
                    String mLANDMARK,
                    String mNOSBRANCHES,
                    String mBUSINESSSETUP,
                    String mBUSINESSBOARD,
                    String mNOSYEARSATADDRESS,
                    String mVISITINGCARD,
                    String mAPPLNAMEVERIFFROM,
                    String mCONTACTVERIF1,
                    String mCONTACTVERIF2,
                    String mCONTACTFEEDBACK,
                    String mPROOFDETAILS,
                    String mPOLITICALLINK,
                    String mOVERALLSTATUS,
                    String mREASONNEGATIVEFI,
                    String mLATITUDE,
                    String mLONGITUDE,
                    String mREMARKS) {

        this.mBusiness = mBusiness;
        this.mCASENO = mCASENO;
        this.mADDRESS = mADDRESS;
        this.mEASELOCATE = mEASELOCATE;
        this.mOFFICEOWNERSHIP = mOFFICEOWNERSHIP;
        this.mAPPLCOMPANYNAME = mAPPLCOMPANYNAME;
        this.mLOCALITYTYPE = mLOCALITYTYPE;
        this.mNATUREBUSNIESS = mNATUREBUSNIESS;
        this.mAPPLDESIGNATION = mAPPLDESIGNATION;
        this.mWORKINGSINCE = mWORKINGSINCE;
        this.mPERSONCONTACTED = mPERSONCONTACTED;
        this.mPERSONDESIGNATION = mPERSONDESIGNATION;
        this.mNOSEMP = mNOSEMP;
        this.mLANDMARK = mLANDMARK;
        this.mNOSBRANCHES = mNOSBRANCHES;
        this.mBUSINESSSETUP = mBUSINESSSETUP;
        this.mBUSINESSBOARD = mBUSINESSBOARD;
        this.mNOSYEARSATADDRESS = mNOSYEARSATADDRESS;
        this.mVISITINGCARD = mVISITINGCARD;
        this.mAPPLNAMEVERIFFROM = mAPPLNAMEVERIFFROM;
        this.mCONTACTVERIF1 = mCONTACTVERIF1;
        this.mCONTACTVERIF2 = mCONTACTVERIF2;
        this.mCONTACTFEEDBACK = mCONTACTFEEDBACK;
        this.mPROOFDETAILS = mPROOFDETAILS;
        this.mPOLITICALLINK = mPOLITICALLINK;
        this.mOVERALLSTATUS = mOVERALLSTATUS;
        this.mREASONNEGATIVEFI = mREASONNEGATIVEFI;
        this.mLATITUDE = mLATITUDE;
        this.mLONGITUDE = mLONGITUDE;
        this.mREMARKS = mREMARKS;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getBusiness(){
        return this.mBusiness;
    }

    public String getCASENO() {
        return mCASENO;
    }

    public String getADDRESS() {
        return mADDRESS;
    }

    public String getEASELOCATE() {
        return mEASELOCATE;
    }

    public String getOFFICEOWNERSHIP() {
        return mOFFICEOWNERSHIP;
    }

    public String getAPPLCOMPANYNAME() {
        return mAPPLCOMPANYNAME;
    }

    public String getLOCALITYTYPE() {
        return mLOCALITYTYPE;
    }

    public String getNATUREBUSNIESS() {
        return mNATUREBUSNIESS;
    }

    public String getAPPLDESIGNATION() {
        return mAPPLDESIGNATION;
    }

    public String getWORKINGSINCE() {
        return mWORKINGSINCE;
    }

    public String getPERSONCONTACTED() {
        return mPERSONCONTACTED;
    }

    public String getPERSONDESIGNATION() {
        return mPERSONDESIGNATION;
    }

    public String getNOSEMP() {
        return mNOSEMP;
    }

    public String getLANDMARK() {
        return mLANDMARK;
    }

    public String getNOSBRANCHES() {
        return mNOSBRANCHES;
    }

    public String getBUSINESSSETUP() {
        return mBUSINESSSETUP;
    }

    public String getBUSINESSBOARD() {
        return mBUSINESSBOARD;
    }

    public String getNOSYEARSATADDRESS() {
        return mNOSYEARSATADDRESS;
    }

    public String getVISITINGCARD() {
        return mVISITINGCARD;
    }

    public String getAPPLNAMEVERIFFROM() {
        return mAPPLNAMEVERIFFROM;
    }

    public String getCONTACTVERIF1() {
        return mCONTACTVERIF1;
    }

    public String getCONTACTVERIF2() {
        return mCONTACTVERIF2;
    }

    public String getCONTACTFEEDBACK() {
        return mCONTACTFEEDBACK;
    }

    public String getPROOFDETAILS() {
        return mPROOFDETAILS;
    }

    public String getPOLITICALLINK() {
        return mPOLITICALLINK;
    }

    public String getOVERALLSTATUS() {
        return mOVERALLSTATUS;
    }

    public String getREASONNEGATIVEFI() {
        return mREASONNEGATIVEFI;
    }

    public String getLATITUDE() {
        return mLATITUDE;
    }

    public String getLONGITUDE() {
        return mLONGITUDE;
    }

    public String getREMARKS() {
        return mREMARKS;
    }
}
