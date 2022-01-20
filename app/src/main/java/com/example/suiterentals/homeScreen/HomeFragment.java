package com.example.suiterentals.homeScreen;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.suiterentals.R;
import com.example.suiterentals.homeScreen.HomeFragmentScreens.DownloadImageTask;
import com.example.suiterentals.homeScreen.HomeFragmentScreens.ShowDataMainActivity;


public class HomeFragment extends Fragment {

    View view;
    Context contexxt;
    Button btnrecoment,btnrelease,btntoprated;
    HomeActivity homeActivity;
    ProgressDialog pd;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv1location,tv2location,tv6location,tv7location;
    ImageView iv1,iv2,iv3,iv4,iv5,iv6,iv7;
//    private static int counter = 1;



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        contexxt = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_home, container, false);
        homeActivity = (HomeActivity) getActivity();
        btnrelease = view.findViewById(R.id.btnShowRelease);
        btnrecoment = view.findViewById(R.id.btnShowRecomended);
        btntoprated = view.findViewById(R.id.btnShowTopRated);
        tv1  = view.findViewById(R.id.tv1title);
        tv2  = view.findViewById(R.id.tv2title);
        tv3  = view.findViewById(R.id.tv3title);
        tv4  = view.findViewById(R.id.tv4title);
        tv5  = view.findViewById(R.id.tv5title);
        tv6  = view.findViewById(R.id.tv6title);
        tv7  = view.findViewById(R.id.tv7title);
        tv1location  = view.findViewById(R.id.tv1location);
        tv2location  = view.findViewById(R.id.tv2location);
        tv6location  = view.findViewById(R.id.tv6location);
        tv7location  = view.findViewById(R.id.tv7location);
        iv1  = view.findViewById(R.id.image_1);
        iv2  = view.findViewById(R.id.image_2);
        iv3  = view.findViewById(R.id.image_3);
        iv4  = view.findViewById(R.id.image_4);
        iv5  = view.findViewById(R.id.image_5);
        iv6  = view.findViewById(R.id.image_6);
        iv7  = view.findViewById(R.id.image_7);

        if(!homeActivity.suiteList.isEmpty())
        {
            try {

                tv1.setText(homeActivity.getProductList().get(0).getSuitetitle());
                tv2.setText(homeActivity.getProductList().get(1).getSuitetitle());
                tv3.setText(homeActivity.getProductList().get(2).getSuitetitle());
                tv4.setText(homeActivity.getProductList().get(0).getSuitetitle());
                tv5.setText(homeActivity.getProductList().get(1).getSuitetitle());
                tv6.setText(homeActivity.getProductList().get(0).getSuitetitle());
                tv7.setText(homeActivity.getProductList().get(1).getSuitetitle());
                tv1location.setText("$ "+homeActivity.getProductList().get(0).getPrice());
                tv2location.setText("$ "+homeActivity.getProductList().get(1).getPrice());
                tv6location.setText("$ "+homeActivity.getProductList().get(0).getPrice());
                tv7location.setText("$ "+homeActivity.getProductList().get(1).getPrice());
                new DownloadImageTask(iv1).execute(homeActivity.getProductList().get(0).getAddress());
                new DownloadImageTask(iv2).execute(homeActivity.getProductList().get(1).getAddress());
                new DownloadImageTask(iv3).execute(homeActivity.getProductList().get(2).getAddress());
                new DownloadImageTask(iv4).execute(homeActivity.getProductList().get(0).getAddress());
                new DownloadImageTask(iv5).execute(homeActivity.getProductList().get(1).getAddress());
                new DownloadImageTask(iv6).execute(homeActivity.getProductList().get(0).getAddress());
                new DownloadImageTask(iv7).execute(homeActivity.getProductList().get(1).getAddress());
            }
            catch (NullPointerException e)
            {
                Log.d("TAG", "onCreateView To textview error: "+e.getMessage());
            }
        }


        btnrelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity().getApplicationContext(), ShowDataMainActivity.class);
                //intent.putExtra("productlist",homeActivity.getProductList());
                startActivity(intent);
            }
        });
        btnrecoment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(contexxt, ShowDataMainActivity.class);
                startActivity(intent);
            }
        });
        btntoprated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(contexxt, ShowDataMainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }


}