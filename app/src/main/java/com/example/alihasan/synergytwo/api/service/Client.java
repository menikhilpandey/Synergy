package com.example.alihasan.synergytwo.api.service;
import com.example.alihasan.synergytwo.api.model.BusinessModel;
import com.example.alihasan.synergytwo.api.model.Debtor;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Client {

    @POST("authorizeandroid.php")
    @FormUrlEncoded
    Call<String> getAuth(@Field("USERNAME") String ID, @Field("PASSWORD") String PASS);

    @POST("fetchcases.php")
    @FormUrlEncoded
    Call<List<Debtor>> getDebtors(@Field("PDANO") String ID);

    @POST("addtotable.php")
    @FormUrlEncoded
    Call<String> sendBusinessData(@Field("TABLENAME") String TABLENAME,
                                  @Field("CASENO") String CASENO,
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
                                  @Field("LONGITUDE") String LONGITUDE);

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
