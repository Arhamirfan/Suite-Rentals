package com.example.suiterentals.homeScreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.suiterentals.MainActivity;
import com.example.suiterentals.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ProfileFragment extends Fragment {

    View view;
    FirebaseAuth mauth;
    Context contexxt;
    SharedPreferences sharedPreferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        contexxt = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        //context = container.getContext();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button logout = (Button) view.findViewById(R.id.btnlogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutDetails();
            }
        });
    }

    public void logoutDetails()
    {
        mauth = FirebaseAuth.getInstance();
        FirebaseUser user = mauth.getCurrentUser();
        sharedPreferences = contexxt.getSharedPreferences("loggedinuser",0);
        SharedPreferences.Editor editoor = sharedPreferences.edit();
        String svedmail= sharedPreferences.getString("email",null);
        Toast.makeText(getActivity(), "Logging out user: \n DB: "+user.getDisplayName() + "\n SP : "+ svedmail, Toast.LENGTH_SHORT).show();
        mauth.signOut();
        editoor.clear();
        editoor.apply();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}