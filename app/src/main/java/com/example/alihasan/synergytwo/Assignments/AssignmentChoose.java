package com.example.alihasan.synergytwo.Assignments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

//    ListView listview;


    private RecyclerView recyclerView;
    private DebtorAdapter mAdapter;

    static String SERVER_URL = Resources.getSystem().getString(R.string.BASE_URL);

//    Intent i = getIntent();

//    String pdaNo = i.getStringExtra("USERNAME");
//    String pdaNo = "PDA123";
    String pdaNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_choose);

        SharedPreferences loginData = getSharedPreferences("PDANOSHARED", Context.MODE_PRIVATE);
        pdaNo = loginData.getString("PDANO", "");

//        listview = findViewById(R.id.listViewData);
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(AssignmentChoose.this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

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

//                List<Debtor> dataList = response.body();
//
//                String[] names = new String[dataList.size()];
//
//                for(int i = 0 ; i < dataList.size() ; i++)
//                {
//                    names[i] = dataList.get(i).getApplName();
//                }
//
//
//                listview.setAdapter(new ArrayAdapter<>(
//                        getApplicationContext(),
//                        android.R.layout.simple_list_item_1, names));

                //fniwnvrnpejnenjp

                Toast.makeText(getApplicationContext(), "GOT RESPONSE", Toast.LENGTH_SHORT).show();

//                Intent i = new Intent(android.content.Intent.ACTION_SEND);
//                i.setType("text/plain");
//                i.putExtra(android.content.Intent.EXTRA_TEXT, "HELLO" + response.body().toString());
//                startActivity(i);

                List<Debtor> dataList = response.body();

                mAdapter = new DebtorAdapter(AssignmentChoose.this,dataList,pdaNo);
                recyclerView.setAdapter(mAdapter);

//                int n = dataList.size();
//
//                String[] caseNo = new String[n];
//                String[] applName = new String[n];
//                String[] address = new String[n];
//                String[] altTele = new String[n];
//                String[] typeCase = new String[n];
//
//                for(int i = 0 ; i < dataList.size() ; i++)
//                {
//                    caseNo[i] = dataList.get(i).getCaseNo();
//                    applName[i] = dataList.get(i).getApplName();
//                    address[i] = dataList.get(i).getAddress();
//                    altTele[i] = dataList.get(i).getAltTele();
//                    typeCase[i] = dataList.get(i).getTypeCase();
//                }
            }

            @Override
            public void onFailure(Call<List<Debtor>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "IN FAILURE"+ pdaNo, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
