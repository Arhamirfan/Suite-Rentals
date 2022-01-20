package com.example.suiterentals.homeScreen.HomeFragmentScreens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.suiterentals.Model.Suite;
import com.example.suiterentals.R;

import java.util.ArrayList;

public class BookProductMainActivity extends AppCompatActivity {

    ArrayList<Suite> myListofSuite = new ArrayList<Suite>();
    Suite suite = new Suite();
    TextView tv1,tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_product_main);

        //suite = (Suite) getIntent().getSerializableExtra("productlist");
        tv1 = findViewById(R.id.tvd1);
        tv2.findViewById(R.id.tvd2);

//        tv1.setText(suite.getSuitetitle());
//        tv2.setText(suite.getPrice());
    }
}