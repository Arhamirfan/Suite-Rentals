package com.example.suiterentals.homeScreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.suiterentals.MainActivity;
import com.example.suiterentals.R;
import com.example.suiterentals.homeScreen.HomeFragmentScreens.ShowDataMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class HomeFragment extends Fragment {

    View view;
    Context contexxt;
    Button btnrecoment,btnrelease,btntoprated;
    FirebaseAuth mauth;
    SharedPreferences sharedPreferences;
    FirebaseUser user;


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

        btnrelease = view.findViewById(R.id.btnShowRelease);
        btnrecoment = view.findViewById(R.id.btnShowRecomended);
        btntoprated = view.findViewById(R.id.btnShowTopRated);

        btnrelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(contexxt, ShowDataMainActivity.class));
            }
        });
        return view;
    }

}