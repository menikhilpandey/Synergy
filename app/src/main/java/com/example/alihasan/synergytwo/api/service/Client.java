package com.example.alihasan.synergytwo.api.service;
import com.example.alihasan.synergytwo.api.model.Debtor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Client {

    @POST("authorize.php")
    @FormUrlEncoded
    Call<String> getAuth(@Field("USERNAME") String ID, @Field("PASSWORD") String PASS);

    @POST("logout.php")
    @FormUrlEncoded
    Call<String> logout(@Field("USERNAME") String ID);

    @POST("fetchcases.php")
    @FormUrlEncoded
    Call<List<Debtor>> getDebtors(@Field("PDANO") String ID);

    @POST("deleteimage.php")
    @FormUrlEncoded
    Call<String> deleteImage(@Field("NAME") String ID);

    @POST("addtotable.php")
    @FormUrlEncoded
    Call<String> sendBusinessData(@Field("TABLENAME") String TABLENAME,
                                  @Field("CASENO") String CASENO,
                                  @Field("ADDRESS") String ADDRESS,
                                  @Field("EASELOCATE") String EASELOCATE,
                                  @Field("OFFICEOWNERSHIP") String OFFICEOWNERSHIP,
                                  @Field("APPLCOMPANYNAME") String APPLCOMPANYNAME,
                                  @Field("LOCALITYTYPE") String LOCALITYTYPE,
                                  @Field("NATUREBUSNIESS") String NATUREBUSNIESS,
                                  @Field("APPLDESIGNATION") String APPLDESIGNATION,
                                  @Field("WORKINGSINCE") String WORKINGSINCE,
                                  @Field("PERSONCONTACTED") String PERSONCONTACTED,
                                  @Field("PERSONDESIGNATION") String PERSONDESIGNATION,
                                  @Field("NOSEMP") String NOSEMP,
                                  @Field("LANDMARK") String LANDMARK,
                                  @Field("NOSBRANCHES") String NOSBRANCHES,
                                  @Field("BUSINESSSETUP") String BUSINESSSETUP,
                                  @Field("BUSINESSBOARD") String BUSINESSBOARD,
                                  @Field("NOSYEARSATADDRESS") String NOSYEARSATADDRESS,
                                  @Field("VISITINGCARD") String VISITINGCARD,
                                  @Field("APPLNAMEVERIFFROM") String APPLNAMEVERIFFROM,
                                  @Field("CONTACTVERIF1") String CONTACTVERIF1,
                                  @Field("CONTACTVERIF2") String CONTACTVERIF2,
                                  @Field("CONTACTFEEDBACK") String CONTACTFEEDBACK,
                                  @Field("PROOFDETAILS") String PROOFDETAILS,
                                  @Field("POLITICALLINK") String POLITICALLINK,
                                  @Field("OVERALLSTATUS") String OVERALLSTATUS,
                                  @Field("REASONNEGATIVEFI") String REASONNEGATIVEFI,
                                  @Field("LATITUDE") String LATITUDE,
                                  @Field("LONGITUDE") String LONGITUDE,
                                  @Field("REMARKS") String REMARKS);

    @POST("addtotable.php")
    @FormUrlEncoded
    Call<String> sendPropertyData(@Field("TABLENAME") String TABLENAME,
                                  @Field("CASENO") String CASENO,
                                  @Field("ADDRESS") String ADDRESS,
                                  @Field("EASELOCATE") String EASELOCATE,
                                  @Field("PERSONCONTACTED") String PERSONCONTACTED,
                                  @Field("RELATIONSHIP") String RELATIONSHIP,
                                  @Field("PROPERTYTYPE") String PROPERTYTYPE,
                                  @Field("NOSYEARSOWNER") String NOSYEARSOWNER,
                                  @Field("AREA") String AREA,
                                  @Field("DOCSVERIF") String DOCSVERIF,
                                  @Field("NEIGHBOURNAME1") String NEIGHBOURNAME1,
                                  @Field("ADDRESS1") String ADDRESS1,
                                  @Field("NEIGHBOURNAME2") String NEIGHBOURNAME2,
                                  @Field("ADDRESS2") String ADDRESS2,
                                  @Field("PROPERTYSOLDTO") String PROPERTYSOLDTO,
                                  @Field("PURCHASERDETAILS") String PURCHASERDETAILS,
                                  @Field("APPLCOOPERATIVE") String APPLCOOPERATIVE,
                                  @Field("NEIGHBOURFEEDBACK") String NEIGHBOURFEEDBACK,
                                  @Field("LOCALITYTYPE") String LOCALITYTYPE,
                                  @Field("AMBIENCE") String AMBIENCE,
                                  @Field("APPLSTAYATADDRESS") String APPLSTAYATADDRESS,
                                  @Field("VEHICLESEEN") String VEHICLESEEN,
                                  @Field("POLITICALLINK") String POLITICALLINK,
                                  @Field("OVERALLSTATUS") String OVERALLSTATUS,
                                  @Field("REASONNEGATIVEFI") String REASONNEGATIVEFI,
                                  @Field("LATITUDE") String LATITUDE,
                                  @Field("LONGITUDE") String LONGITUDE,
                                  @Field("REMARKS") String REMARKS);

    @POST("addtotable.php")
    @FormUrlEncoded
    Call<String> sendResidenceData(@Field("TABLENAME") String TABLENAME,
                                   @Field("CASENO") String CASENO,
                                   @Field("ADDRESS") String ADDRESS,
                                   @Field("EASELOCATE") String EASELOCATE,
                                   @Field("AGE") String AGE,
                                   @Field("LOCALITYTYPE") String LOCALITYTYPE,
                                   @Field("HOUSETYPE") String HOUSETYPE,
                                   @Field("HOUSECONDITION") String HOUSECONDITION,
                                   @Field("OWNERSHIP") String OWNERSHIP,
                                   @Field("LIVINGSTANDARD") String LIVINGSTANDARD,
                                   @Field("LANDMARK") String LANDMARK,
                                   @Field("STAYINGSINCE") String STAYINGSINCE,
                                   @Field("APPLSTAYATADDRESS") String APPLSTAYATADDRESS,
                                   @Field("PERSONCONTACTED") String PERSONCONTACTED,
                                   @Field("RELATIONSHIP") String RELATIONSHIP,
                                   @Field("ACCOMODATIONTYPE") String ACCOMODATIONTYPE,
                                   @Field("EXTERIOR") String EXTERIOR,
                                   @Field("NOOFFAMILY") String NOOFFAMILY,
                                   @Field("WORKING") String WORKING,
                                   @Field("DEPENDENTADULTS") String DEPENDENTADULTS,
                                   @Field("DEPENDENTCHILDREN") String DEPENDENTCHILDREN,
                                   @Field("RETIREDMEMBER") String RETIREDMEMBER,
                                   @Field("SPOUSEEARNING") String SPOUSEEARNING,
                                   @Field("SPOUSEDETAILS") String SPOUSEDETAILS,
                                   @Field("MARITALSTATUS") String MARITALSTATUS,
                                   @Field("EDUQUAL") String EDUQUAL,
                                   @Field("NEIGHBOURNAME1") String NEIGHBOURNAME1,
                                   @Field("ADDRESS1") String ADDRESS1,
                                   @Field("NEIGHBOURNAME2") String NEIGHBOURNAME2,
                                   @Field("ADDRESS2") String ADDRESS2,
                                   @Field("NEIGHBOURFEEDBACK") String NEIGHBOURFEEDBACK,
                                   @Field("PROOFDETAILS") String PROOFDETAILS,
                                   @Field("VEHICLESEEN") String VEHICLESEEN,
                                   @Field("POLITICALLINK") String POLITICALLINK,
                                   @Field("OVERALLSTATUS") String OVERALLSTATUS,
                                   @Field("REASONNEGATIVEFI") String REASONNEGATIVEFI,
                                   @Field("LATITUDE") String LATITUDE,
                                   @Field("LONGITUDE") String LONGITUDE,
                                   @Field("REMARKS") String REMARKS);

    @POST("addtotable.php")
    @FormUrlEncoded
    Call<String> sendEmploymentData(@Field("TABLENAME") String TABLENAME,
                                    @Field("CASENO") String CASENO,
                                    @Field("ADDRESS") String ADDRESS,
                                    @Field("EASELOCATE") String EASELOCATE,
                                    @Field("APPLCOMPANYNAME") String APPLCOMPANYNAME,
                                    @Field("LOCALITYTYPE") String LOCALITYTYPE,
                                    @Field("ADDRESSCONF") String ADDRESSCONF,
                                    @Field("LANDMARK") String LANDMARK,
                                    @Field("APPLDESIGNATION") String APPLDESIGNATION,
                                    @Field("PERSONMET") String PERSONMET,
                                    @Field("PERSONDESIGNATION") String PERSONDESIGNATION,
                                    @Field("PERSONCONTACT") String PERSONCONTACT,
                                    @Field("DOESAPPLWORK") String DOESAPPLWORK,
                                    @Field("OFFICETEL") String OFFICETEL,
                                    @Field("OFFICEBOARD") String OFFICEBOARD,
                                    @Field("ORGANISATIONTYPE") String ORGANISATIONTYPE,
                                    @Field("DOJ") String DOJ,
                                    @Field("VISITINGCARD") String VISITINGCARD,
                                    @Field("NATUREBUSNIESS") String NATUREBUSNIESS,
                                    @Field("JOBTYPE") String JOBTYPE,
                                    @Field("WORKINGAS") String WORKINGAS,
                                    @Field("JOBTRANSFER") String JOBTRANSFER,
                                    @Field("NOSEMP") String NOSEMP,
                                    @Field("PERSONCONTACTED") String PERSONCONTACTED,
                                    @Field("PERSONCONDESIGNATION") String PERSONCONDESIGNATION,
                                    @Field("MANAGER") String MANAGER,
                                    @Field("MANAGERDESIGNATION") String MANAGERDESIGNATION,
                                    @Field("MANAGERCONTACT") String MANAGERCONTACT,
                                    @Field("SALARY") String SALARY,
                                    @Field("TPCCONFIRM") String TPCCONFIRM,
                                    @Field("TPCNAME") String TPCNAME,
                                    @Field("OVERALLSTATUS") String OVERALLSTATUS,
                                    @Field("REASONNEGATIVEFI") String REASONNEGATIVEFI,
                                    @Field("LATITUDE") String LATITUDE,
                                    @Field("LONGITUDE") String LONGITUDE,
                                    @Field("REMARKS") String REMARKS);


    @POST("imageupload.php")
    @FormUrlEncoded
    Call<String> imageUpload(@Field("IMAGE") String image,
                             @Field("NAME") String imageName,
                             @Field("CASENO") String caseNo,
                             @Field("CASETYPE") String PASS,
                             @Field("PDANO") String pdaNo);

    @POST("exitcase.php")
    @FormUrlEncoded
    Call<String> exit(@Field("CASENO") String ID, @Field("CASETYPE") String PASS);


}
