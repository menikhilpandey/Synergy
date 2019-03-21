package com.example.alihasan.synergytwo.Assignments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alihasan.synergytwo.Adapters.DebtorAdapter;
import com.example.alihasan.synergytwo.Database.Business;
import com.example.alihasan.synergytwo.Database.BusinessViewModel;
import com.example.alihasan.synergytwo.Database.DebtorDatabase.DebtorViewModel;
import com.example.alihasan.synergytwo.Database.EmploymentDatabase.Employment;
import com.example.alihasan.synergytwo.Database.EmploymentDatabase.EmploymentViewModel;
import com.example.alihasan.synergytwo.Database.ImageDatabase.ImageParam;
import com.example.alihasan.synergytwo.Database.ImageDatabase.ImageViewModel;
import com.example.alihasan.synergytwo.Database.ResidenceDatabase.Residence;
import com.example.alihasan.synergytwo.Database.ResidenceDatabase.ResidenceViewModel;
import com.example.alihasan.synergytwo.LoginActivity;
import com.example.alihasan.synergytwo.R;
import com.example.alihasan.synergytwo.api.model.Debtor;
import com.example.alihasan.synergytwo.api.service.Client;
import com.example.alihasan.synergytwo.api.service.ServerURL;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AssignmentChoose extends AppCompatActivity {

    /**
     * DD test
     */
    private BusinessViewModel businessViewModel;
    private EmploymentViewModel employmentViewModel;
    private ResidenceViewModel residenceViewModel;
    private ImageViewModel imageViewModel;
    private DebtorViewModel   debtorViewModel;

    /**
     *
     */

    private RecyclerView recyclerView;
    private DebtorAdapter mAdapter;

    TextView textView;

    static String SERVER_URL = new ServerURL().getSERVER_URL();

    String pdaNo;

    SwipeRefreshLayout mSwipeRefreshLayout;

    LinearLayout pendingUploadLinearLayout;
    TextView pendingUploadTextView;
    Button pendingUploadButton;

    int pendingCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_choose);

        pendingUploadLinearLayout = findViewById(R.id.pendingUploadLinearLayout);
        pendingUploadTextView = findViewById(R.id.pendingUploadTextView);
        pendingUploadButton = findViewById(R.id.pendingUploadButton);

        /**
         * DD test
         */
        businessViewModel = ViewModelProviders.of(this).get(BusinessViewModel.class);
        employmentViewModel = ViewModelProviders.of(this).get(EmploymentViewModel.class);
        residenceViewModel = ViewModelProviders.of(this).get(ResidenceViewModel.class);
        imageViewModel = ViewModelProviders.of(this).get(ImageViewModel.class);
        debtorViewModel   = ViewModelProviders.of(this).get(DebtorViewModel.class);


        if(businessViewModel.getCount() + employmentViewModel.getCount() + residenceViewModel.getCount()>0)
        {
            pendingCount = businessViewModel.getCount()+employmentViewModel.getCount()+residenceViewModel.getCount();
            pendingUploadTextView.setText(pendingCount + " PENDING UPLOAD");
            pendingUploadLinearLayout.setVisibility(View.VISIBLE);
        }


        SharedPreferences loginData = getSharedPreferences("PDANOSHARED", Context.MODE_PRIVATE);
        pdaNo = loginData.getString("PDANO", "");

//        listview = findViewById(R.id.listViewData);
        recyclerView = findViewById(R.id.recyclerView);
        textView = findViewById(R.id.empty_view);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        LinearLayoutManager manager = new LinearLayoutManager(AssignmentChoose.this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.invalidate();

        /**
         * DD changes
         */

        debtorViewModel.getAllDebtor().observe(this, new Observer<List<Debtor>>() {
            @Override
            public void onChanged(@Nullable List<Debtor> debtors) {
                mAdapter = new DebtorAdapter(AssignmentChoose.this, debtors, pdaNo,((MyApplication)getApplicationContext()).myGlobalArray);
                recyclerView.setAdapter(mAdapter);

                if (mAdapter.getItemCount()==0) {
                    recyclerView.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                }
                else {
                    recyclerView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                }
            }
        });

        /**
         * DD/
         */

        /**
         * START OF RETROFIT
         */


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            Client client = retrofit.create(Client.class);

            Call<List<Debtor>> call = client.getDebtors(pdaNo);

            call.enqueue(new Callback<List<Debtor>>() {
                @Override
                public void onResponse(Call<List<Debtor>> call, Response<List<Debtor>> response) {

                    debtorViewModel.deleteAll();

                if(response.body()==null)
                {
                    Toast.makeText(getApplicationContext(), "SERVER IS DOWN", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "GOT RESPONSE", Toast.LENGTH_SHORT).show();

                    List<Debtor> dataList = response.body();

                    for(int i = 0; i < dataList.size(); i++){
                        debtorViewModel.insert(dataList.get(i));
                    }

//                    mAdapter = new DebtorAdapter(AssignmentChoose.this, dataList, pdaNo,((MyApplication)getApplicationContext()).myGlobalArray);
//                    recyclerView.setAdapter(mAdapter);
//
//                    if (mAdapter.getItemCount()==0) {
//                        recyclerView.setVisibility(View.GONE);
//                        textView.setVisibility(View.VISIBLE);
//                    }
//                    else {
//                        recyclerView.setVisibility(View.VISIBLE);
//                        textView.setVisibility(View.GONE);
//                    }

                }

                @Override
                public void onFailure(Call<List<Debtor>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "No Internet/ Failure", Toast.LENGTH_SHORT).show();
                }
            });

            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    if(businessViewModel.getCount()+employmentViewModel.getCount() + residenceViewModel.getCount()>0)
                    {
                        pendingCount = businessViewModel.getCount()+employmentViewModel.getCount()+residenceViewModel.getCount();
                        pendingUploadTextView.setText(pendingCount + " PENDING UPLOAD");
                        pendingUploadLinearLayout.setVisibility(View.VISIBLE);
                    }

                    if(businessViewModel.getCount()+employmentViewModel.getCount()+residenceViewModel.getCount()<=0)
                    {
                        pendingUploadLinearLayout.setVisibility(View.GONE);
                    }

                    Toast.makeText(getApplicationContext(), "Refreshed", Toast.LENGTH_SHORT).show();


                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(SERVER_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    Client client = retrofit.create(Client.class);

                    Call<List<Debtor>> call = client.getDebtors(pdaNo);

                    call.enqueue(new Callback<List<Debtor>>() {
                        @Override
                        public void onResponse(Call<List<Debtor>> call, Response<List<Debtor>> response) {

                            debtorViewModel.deleteAll();

                            if(response.body()==null)
                            {
                                Toast.makeText(getApplicationContext(), "SERVER IS DOWN", Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(getApplicationContext(), "GOT RESPONSE", Toast.LENGTH_SHORT).show();

                            List<Debtor> dataList = response.body();

                            for(int i = 0; i < dataList.size(); i++){
                                debtorViewModel.insert(dataList.get(i));
                            }

//                            mAdapter = new DebtorAdapter(AssignmentChoose.this, dataList, pdaNo,((MyApplication)getApplicationContext()).myGlobalArray);
//                            recyclerView.setAdapter(mAdapter);
//
//                            if (mAdapter.getItemCount()==0) {
//                                recyclerView.setVisibility(View.GONE);
//                                textView.setVisibility(View.VISIBLE);
//                            }
//                            else {
//                                recyclerView.setVisibility(View.VISIBLE);
//                                textView.setVisibility(View.GONE);
//                            }

                            mSwipeRefreshLayout.setRefreshing(false);

                        }

                        @Override
                        public void onFailure(Call<List<Debtor>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "No Internet/ Failure", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });

            pendingUploadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadData();

                }
            });


    }

    public void uploadData(){

        /*
        BUSINESS VIEWMODEL
         */

        final List<Business> businessList = businessViewModel.testGetAllData();

//        int count = businessViewModel.getCount();
        for(int i = businessViewModel.getCount(), j = 1 ;i > 0; i--, j++) {

            Handler handler = new Handler();
            final int finalJ = j;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    businessRetroFitUpload(businessList, finalJ);
                }
            },1000 * j);
        }

        /*
        EMPLOYMENT VIEWMODEL
         */
        final List<Employment> employmentList = employmentViewModel.getAllData();

//        int count = businessViewModel.getCount();
        for(int i = employmentViewModel.getCount(), j3 = 1 ;i > 0; i--, j3++) {

            Handler handler = new Handler();
            final int finalJ3 = j3;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    employmentRetroFitUpload(employmentList, finalJ3);
                }
            },1000 * j3);
        }

        /*
        RESIDENCE VIEW MODEL
         */

        final List<Residence> residenceList = residenceViewModel.getAllData();

//        int count = businessViewModel.getCount();
        for(int i = residenceViewModel.getCount(), j4 = 1 ;i > 0; i--, j4++) {

            Handler handler = new Handler();
            final int finalJ4 = j4;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    residenceRetroFitUpload(residenceList, finalJ4);
                }
            },1000 * j4);
        }


        //LOOP FOR IMAGE UPLOAD
        final List<ImageParam> imageList = imageViewModel.getAllData();

        for(int i = imageViewModel.getCount(), j2 = 1 ;i > 0; i--, j2++) {

            Handler handler = new Handler();
            final int finalJ2 = j2;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    retroImageUpload(imageList, finalJ2);
                }
            },1000 * j2);
        }


    }

        public void retroImageUpload(final List<ImageParam> imageParamList, final int j)
        {

            //GET THESE STRINGS FROM DATABSE;
            String encodedImage = imageParamList.get(j-1).getEncodedImage();
            String globalImageFileName = imageParamList.get(j-1).getGlobalImageFileName();
            String StringCaseNo = imageParamList.get(j-1).getStringCaseNo();
            String ACTIVITY = imageParamList.get(j-1).getACTIVITY();
            String userName = imageParamList.get(j-1).getUserName();

                    Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Client client = retrofit.create(Client.class);

        Call<String> call = client.imageUpload(encodedImage,globalImageFileName,StringCaseNo,ACTIVITY,userName);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.body()==null)
                {
                    Toast.makeText(getApplicationContext(), "SERVER IS DOWN", Toast.LENGTH_SHORT).show();
                }

                else if(response.body().equals("Success"))
                {
                    Toast.makeText(getApplicationContext(), "IMAGE UPLOAD SUCCESSFUL", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "IMAGE UPLOAD UNSUCCESSFUL", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "IN FAILURE", Toast.LENGTH_SHORT).show();


            }
        });
        }

    public void residenceRetroFitUpload(final List<Residence> residenceList, final int j)
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        final Client client = retrofit.create(Client.class);

        Call<String> call = client.sendResidenceData(
                residenceList.get(j-1).getRESIDENCE(),
                residenceList.get(j-1).getCASENO(),
                residenceList.get(j-1).getADDRESS(),
                residenceList.get(j-1).getEASELOCATE(),
                residenceList.get(j-1).getAGE(),
                residenceList.get(j-1).getLOCALITYTYPE(),
                residenceList.get(j-1).getHOUSETYPE(),
                residenceList.get(j-1).getHOUSECONDITION(),
                residenceList.get(j-1).getOWNERSHIP(),
                residenceList.get(j-1).getLIVINGSTANDARD(),
                residenceList.get(j-1).getLANDMARK(),
                residenceList.get(j-1).getSTAYINGSINCE(),
                residenceList.get(j-1).getAPPLSTAYATADDRESS(),
                residenceList.get(j-1).getPERSONCONTACTED(),
                residenceList.get(j-1).getRELATIONSHIP(),
                residenceList.get(j-1).getACCOMODATIONTYPE(),
                residenceList.get(j-1).getEXTERIOR(),
                residenceList.get(j-1).getNOOFFAMILY(),
                residenceList.get(j-1).getWORKING(),
                residenceList.get(j-1).getDEPENDENTADULTS(),
                residenceList.get(j-1).getDEPENDENTCHILDREN(),
                residenceList.get(j-1).getRETIREDMEMBER(),
                residenceList.get(j-1).getSPOUSEEARNING(),
                residenceList.get(j-1).getSPOUSEDETAILS(),
                residenceList.get(j-1).getMARITALSTATUS(),
                residenceList.get(j-1).getEDUQUAL(),
                residenceList.get(j-1).getNEIGHBOURNAME1(),
                residenceList.get(j-1).getADDRESS1(),
                residenceList.get(j-1).getNEIGHBOURNAME2(),
                residenceList.get(j-1).getADDRESS2(),
                residenceList.get(j-1).getNEIGHBOURFEEDBACK(),
                residenceList.get(j-1).getPROOFDETAILS(),
                residenceList.get(j-1).getVEHICLESEEN(),
                residenceList.get(j-1).getPOLITICALLINK(),
                residenceList.get(j-1).getOVERALLSTATUS(),
                residenceList.get(j-1).getREASONNEGATIVEFI(),
                residenceList.get(j-1).getLATITUDE(),
                residenceList.get(j-1).getLONGITUDE(),
                residenceList.get(j-1).getREMARKS());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.body()==null)
                {
                    Toast.makeText(getApplicationContext(), "SERVER IS DOWN", Toast.LENGTH_SHORT).show();
                }

                else if(response.body().equals("Success")) {

                    String CASENO = residenceList.get(j-1).getCASENO();
                    ((MyApplication)getApplicationContext()).myGlobalArray.remove(CASENO);

                    retrofitExit(CASENO,"RESIDENCE");

                    Toast.makeText(getApplicationContext(), "SUCCESSFULLY UPLOADED  ", Toast.LENGTH_SHORT).show();
                    retrofitExit(residenceList.get(j-1).getCASENO(),residenceList.get(j-1).getRESIDENCE());
                    residenceViewModel.delete(residenceList.get(j-1));

//                    businessList.remove(j-1);

                    /**
                     * Will receive something to verify
                     * successful upload to table
                     */

//                    Intent intent = new Intent(BusinessActivity.this, AssignmentChoose.class);
//                    intent.putExtra("CASENO", StringCaseNo);
//                    intent.putExtra("USERNAME", userName);
//                    intent.putExtra("TYPEOFCASE", ACTIVITY);
//                    startActivity(intent);
                }

                else
                {
                    Toast.makeText(getApplicationContext(), "SOMETHING WENT WRONG "+response.body(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "IN FAILURE", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void employmentRetroFitUpload(final List<Employment> employmentList, final int j)
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        final Client client = retrofit.create(Client.class);

        Call<String> call = client.sendEmploymentData(
                employmentList.get(j-1).getEMPLOYMENT(),
                employmentList.get(j-1).getCASENO(),
                employmentList.get(j-1).getADDRESS(),
                employmentList.get(j-1).getEASELOCATE(),
                employmentList.get(j-1).getAPPLCOMPANYNAME(),
                employmentList.get(j-1).getLOCALITYTYPE(),
                employmentList.get(j-1).getADDRESSCONF(),
                employmentList.get(j-1).getLANDMARK(),
                employmentList.get(j-1).getAPPLDESIGNATION(),
                employmentList.get(j-1).getPERSONMET(),
                employmentList.get(j-1).getPERSONDESIGNATION(),
                employmentList.get(j-1).getPERSONCONTACT(),
                employmentList.get(j-1).getDOESAPPLWORK(),
                employmentList.get(j-1).getOFFICETEL(),
                employmentList.get(j-1).getOFFICEBOARD(),
                employmentList.get(j-1).getORGANISATIONTYPE(),
                employmentList.get(j-1).getDOJ(),
                employmentList.get(j-1).getVISITINGCARD(),
                employmentList.get(j-1).getNATUREBUSNIESS(),
                employmentList.get(j-1).getJOBTYPE(),
                employmentList.get(j-1).getWORKINGAS(),
                employmentList.get(j-1).getJOBTRANSFER(),
                employmentList.get(j-1).getNOSEMP(),
                employmentList.get(j-1).getPERSONCONTACTED(),
                employmentList.get(j-1).getPERSONCONDESIGNATION(),
                employmentList.get(j-1).getMANAGER(),
                employmentList.get(j-1).getMANAGERDESIGNATION(),
                employmentList.get(j-1).getMANAGERCONTACT(),
                employmentList.get(j-1).getSALARY(),
                employmentList.get(j-1).getTPCCONFIRM(),
                employmentList.get(j-1).getTPCNAME(),
                employmentList.get(j-1).getOVERALLSTATUS(),
                employmentList.get(j-1).getREASONNEGATIVEFI(),
                employmentList.get(j-1).getLATITUDE(),
                employmentList.get(j-1).getLONGITUDE(),
                employmentList.get(j-1).getREMARKS());

                call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.body()==null)
                {
                    Toast.makeText(getApplicationContext(), "SERVER IS DOWN", Toast.LENGTH_SHORT).show();
                }

                else if(response.body().equals("Success")) {

                    String CASENO = employmentList.get(j-1).getCASENO();
                    ((MyApplication)getApplicationContext()).myGlobalArray.remove(CASENO);

                    retrofitExit(CASENO,"EMPLOYMENT");

                    Toast.makeText(getApplicationContext(), "SUCCESSFULLY UPLOADED  ", Toast.LENGTH_SHORT).show();
                    retrofitExit(employmentList.get(j-1).getCASENO(),employmentList.get(j-1).getEMPLOYMENT());
                    employmentViewModel.delete(employmentList.get(j-1));

//                    businessList.remove(j-1);

                    /**
                     * Will receive something to verify
                     * successful upload to table
                     */

//                    Intent intent = new Intent(BusinessActivity.this, AssignmentChoose.class);
//                    intent.putExtra("CASENO", StringCaseNo);
//                    intent.putExtra("USERNAME", userName);
//                    intent.putExtra("TYPEOFCASE", ACTIVITY);
//                    startActivity(intent);
                }

                else
                {
                    Toast.makeText(getApplicationContext(), "SOMETHING WENT WRONG "+response.body(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "IN FAILURE", Toast.LENGTH_SHORT).show();
            }
        });
    }

        public void businessRetroFitUpload(final List<Business> businessList, final int j)
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        final Client client = retrofit.create(Client.class);

        Call<String> call = client.sendBusinessData(
                businessList.get(j-1).getBusiness(),
                businessList.get(j-1).getCASENO(),
                businessList.get(j-1).getADDRESS(),
                businessList.get(j-1).getEASELOCATE(),
                businessList.get(j-1).getOFFICEOWNERSHIP(),
                businessList.get(j-1).getAPPLCOMPANYNAME(),
                businessList.get(j-1).getLOCALITYTYPE(),
                businessList.get(j-1).getNATUREBUSNIESS(),
                businessList.get(j-1).getAPPLDESIGNATION(),
                businessList.get(j-1).getWORKINGSINCE(),
                businessList.get(j-1).getPERSONCONTACTED(),
                businessList.get(j-1).getPERSONDESIGNATION(),
                businessList.get(j-1).getNOSEMP(),
                businessList.get(j-1).getLANDMARK(),
                businessList.get(j-1).getNOSBRANCHES(),
                businessList.get(j-1).getBUSINESSSETUP(),
                businessList.get(j-1).getBUSINESSBOARD(),
                businessList.get(j-1).getNOSYEARSATADDRESS(),
                businessList.get(j-1).getVISITINGCARD(),
                businessList.get(j-1).getAPPLNAMEVERIFFROM(),
                businessList.get(j-1).getCONTACTVERIF1(),
                businessList.get(j-1).getCONTACTVERIF2(),
                businessList.get(j-1).getCONTACTFEEDBACK(),
                businessList.get(j-1).getPROOFDETAILS(),
                businessList.get(j-1).getPOLITICALLINK(),
                businessList.get(j-1).getOVERALLSTATUS(),
                businessList.get(j-1).getREASONNEGATIVEFI(),
                businessList.get(j-1).getLATITUDE(),
                businessList.get(j-1).getLONGITUDE(),
                businessList.get(j-1).getREMARKS());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.body()==null)
                {
                    Toast.makeText(getApplicationContext(), "SERVER IS DOWN", Toast.LENGTH_SHORT).show();
                }

                else if(response.body().equals("Success")) {

                    String CASENO = businessList.get(j-1).getCASENO();
                    ((MyApplication)getApplicationContext()).myGlobalArray.remove(CASENO);

                    retrofitExit(CASENO,"BUSINESS");

                    Toast.makeText(getApplicationContext(), "SUCCESSFULLY UPLOADED  ", Toast.LENGTH_SHORT).show();
                    retrofitExit(businessList.get(j-1).getCASENO(),businessList.get(j-1).getBusiness());
                    businessViewModel.deleteTest(businessList.get(j-1));

//                    businessList.remove(j-1);

                    /**
                     * Will receive something to verify
                     * successful upload to table
                     */

//                    Intent intent = new Intent(BusinessActivity.this, AssignmentChoose.class);
//                    intent.putExtra("CASENO", StringCaseNo);
//                    intent.putExtra("USERNAME", userName);
//                    intent.putExtra("TYPEOFCASE", ACTIVITY);
//                    startActivity(intent);
                }

                else
                    {
                    Toast.makeText(getApplicationContext(), "SOMETHING WENT WRONG "+response.body(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "IN FAILURE", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.logout:

                String enUser;
                SharedPreferences preferences =getSharedPreferences("PDANOSHARED",Context.MODE_PRIVATE);
                enUser = preferences.getString("ENPDANO", "");

                logout(enUser);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void logout(String enUser)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Client client = retrofit.create(Client.class);

        Call<String> call = client.logout(enUser);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.body()==null)
                {
                    Toast.makeText(getApplicationContext(), "SERVER IS DOWN", Toast.LENGTH_SHORT).show();
                }

                else if(response.body().equals("Success"))
                {
                    SharedPreferences preferences =getSharedPreferences("PDANOSHARED",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();
                    finish();

                    SharedPreferences preferences2 = getSharedPreferences("CASEDATA",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor2 = preferences2.edit();
                    editor2.clear();
                    editor2.apply();
                    finish();
                    Intent i = new Intent(AssignmentChoose.this,LoginActivity.class);
                    startActivity(i);

                    Toast.makeText(getApplicationContext(), "Successfully logged out", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Toast.makeText(getApplicationContext(), "Something went wrong :(" + response.body(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No Internet/FAILURE", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public  void retrofitExit(String caseNo, String caseType) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Client client = retrofit.create(Client.class);

        Call<String> call = client.exit(caseNo, caseType);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.body().equals("Success")) {
                    Toast.makeText(getApplicationContext(), "COMPLETED", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "SOMETHING WENT WRONG"+response.body()+"exit", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "IN FAILURE", Toast.LENGTH_SHORT).show();


            }
        });
    }

}
