package com.example.alihasan.synergytwo.Assignments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.alihasan.synergytwo.Database.Business;
import com.example.alihasan.synergytwo.Database.BusinessViewModel;
import com.example.alihasan.synergytwo.R;

import java.util.List;

public class TestActivity extends AppCompatActivity {

    private BusinessViewModel businessViewModel;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        businessViewModel = ViewModelProviders.of(this).get(BusinessViewModel.class);
//
        businessViewModel.insert(new Business("address","adafdsfa","af","afd","asf","afsd","adsfa","asfafd","adfafd","dafsdf","asdfasdf","afdsasdf","afafd","adfa","adfasdf","adsfa","afd","afasf","asdf","asfdasdf","asdf","asfd","safdaf","asdf","fasfd","dfasdf","saf","asdf","asfd","asdfad"));
//
//        Toast.makeText(this, businessViewModel.getAllBusinessData().get(0).getAddress(), Toast.LENGTH_SHORT).show();

//        Toast.makeText(this,  businessViewModel.getAllWords().getValue().get(0).getBusiness(), Toast.LENGTH_SHORT).show();

//        businessViewModel.getAllWords().observe(this, new Observer<List<Business>>() {
//            @Override
//            public void onChanged(@Nullable List<Business> words) {
//                Toast.makeText(TestActivity.this, words.get(0).getBusiness(), Toast.LENGTH_SHORT).show();
//            }
//        });

        button = (Button) findViewById(R.id.testbut);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = businessViewModel.testGetAllData().get(0).getBusiness();
                String val = businessViewModel.testGetAllData().get(0).getCASENO();

                Toast.makeText(TestActivity.this, value + val + " " + businessViewModel.getCount(), Toast.LENGTH_SHORT).show();

                businessViewModel.delete();

            }
        });





    }
}
