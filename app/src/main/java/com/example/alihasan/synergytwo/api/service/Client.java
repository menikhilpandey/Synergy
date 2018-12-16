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

    @POST("authorisedandroid.php")
    @FormUrlEncoded
    Call<String> getAuth(@Field("USER") String ID, @Field("PASS") String PASS);

    @POST("fetchaccess.php")
    @FormUrlEncoded
    Call<ArrayList<Debtor>> getDebtors(@Field("USER") String ID);

    @POST("addToTable.php")
    Call<ArrayList<BusinessModel>> sendBusinessData(@Body BusinessModel data);




}
