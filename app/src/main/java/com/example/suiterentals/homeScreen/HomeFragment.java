package com.example.suiterentals.homeScreen;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.suiterentals.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class HomeFragment extends Fragment {

    View view;
    Context contexxt;
    Button btnrecoment,btnrelease,btntoprated;

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
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_home, container, false);

        btnrelease = view.findViewById(R.id.btnShowRelease);

        btnrelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth;
                auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();

                Toast.makeText(contexxt, "loggedin user: "+user.getUid(), Toast.LENGTH_SHORT).show();
                Toast.makeText(contexxt, "loggedin user: "+user.getEmail(), Toast.LENGTH_SHORT).show();
                //not getting:
 //               Toast.makeText(contexxt, "loggedin user: "+user.getDisplayName(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}