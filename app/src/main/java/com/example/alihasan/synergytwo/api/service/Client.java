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

    @POST("authorizedandroid.php")
    @FormUrlEncoded
    Call<String> getAuth(@Field("USERNAME") String ID, @Field("PASSWORD") String PASS);

    @POST("fetchcases.php")
    @FormUrlEncoded
    Call<ArrayList<Debtor>> getDebtors(@Field("PDANO") String ID);

    @POST("addtotable.php")
    Call<String> sendBusinessData(@Body BusinessModel data);

    @POST("imageupload.php")
    Call<String> imageUpload(@Field("IMAGE") String image,@Field("NAME") String imageName,
                             @Field("CASENO") String caseNo, @Field("CASETYPE") String PASS, @Field("PDANO") String pdaNo);

    @POST("exitcase.php")
    Call<String> exit(@Field("CASENO") String ID, @Field("CASETYPE") String PASS);


}
