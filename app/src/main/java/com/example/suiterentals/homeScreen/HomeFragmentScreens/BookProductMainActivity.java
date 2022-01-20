package com.example.suiterentals.homeScreen.HomeFragmentScreens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.suiterentals.Model.Suite;
import com.example.suiterentals.R;
import com.example.suiterentals.homeScreen.profileFragmentScreens.LocationMainActivity;

import java.util.ArrayList;

public class BookProductMainActivity extends AppCompatActivity {

    Suite suite = new Suite();
    TextView tv1,tv2,tvtitle,tvcity,tvprice,tvdescription,tvphoneno;
    Button btncall,btnlocation;
    ImageView img_details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_product_main);

        this.getSupportActionBar().hide();
        suite = (Suite) getIntent().getSerializableExtra("productlist");

        img_details = findViewById(R.id.ivdpic);
        tvtitle = findViewById(R.id.tvdtitle);
        tvcity=findViewById(R.id.tvdcitylocation);
        tvprice = findViewById(R.id.tvdprice);
        tvdescription = findViewById(R.id.tvddescription);
        btncall = findViewById(R.id.btndcall);


        new DownloadImageTask(img_details).execute(suite.getAddress());
        tvtitle.setText(suite.getSuitetitle());
        tvcity.setText("Location: "+suite.getLongitude() +" , "+suite.getLatitude());
        tvprice.setText("$ "+suite.getPrice());
        tvdescription.setText(suite.getDescription());

        String getphone = suite.getPhoneno();
        btncall.setText("Call Owner: "+getphone);

        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+getphone));
                startActivity(intent);
            }
        });
        btnlocation = findViewById(R.id.btndloc);
        btnlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookProductMainActivity.this, LocationMainActivity.class);
                intent.putExtra("latitude",suite.getLatitude());
                intent.putExtra("longitude",suite.getLongitude());
                startActivity(intent);
            }
        });

    }
}