package com.example.alihasan.synergytwo.Assignments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.alihasan.synergytwo.Adapters.DebtorAdapter;
import com.example.alihasan.synergytwo.R;
import com.example.alihasan.synergytwo.api.model.Debtor;
import com.example.alihasan.synergytwo.api.service.Client;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AssignmentChoose extends AppCompatActivity {

    private List<Debtor> debtorList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DebtorAdapter mAdapter;

    static String SERVER_URL = "http://142.93.217.100/repignite/android/";

    Intent i = getIntent();
    String strID = i.getStringExtra("USERNAME");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_choose);

        recyclerView = findViewById(R.id.recyclerView);

        /**
         * START OF RETROFIT
         */

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Client client = retrofit.create(Client.class);

        Call<ArrayList<Debtor>> call = client.getDebtors(strID);

        call.enqueue(new Callback<ArrayList<Debtor>>() {
            @Override
            public void onResponse(Call<ArrayList<Debtor>> call, Response<ArrayList<Debtor>> response) {
                ArrayList<Debtor> dataList = response.body();

                int n = dataList.size();

                String[] caseNo = new String[n];
                String[] applName = new String[n];
                String[] address = new String[n];
                String[] altTele = new String[n];
                String[] typeCase = new String[n];

                for(int i = 0 ; i < dataList.size() ; i++)
                {
                    caseNo[i] = dataList.get(i).getCaseNo();
                    applName[i] = dataList.get(i).getApplName();
                    address[i] = dataList.get(i).getAddress();
                    altTele[i] = dataList.get(i).getAltTele();
                    typeCase[i] = dataList.get(i).getTypeCase();
                }

                mAdapter = new DebtorAdapter(AssignmentChoose.this,dataList);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Debtor>> call, Throwable t) {

            }
        });


    }
}
