package com.example.suiterentals.homeScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.suiterentals.R;
import com.example.suiterentals.homeScreen.HomeFragmentScreens.ShowDataMainActivity;
import com.example.suiterentals.homeScreen.SearchFragmentScreen.searchMainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class SearchFragment extends Fragment {


    View view;
    Context contexxt;
    EditText edtsearch;
    FloatingActionButton searchbtn;
    Button btnbeds,btnsuite,btnmension,btnlahore,btnstars;

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
        view = inflater.inflate(R.layout.fragment_search, container, false);
        edtsearch = view.findViewById(R.id.et_search);
        searchbtn = view.findViewById(R.id.fabsearch);
        btnbeds = view.findViewById(R.id.btnbed);
        btnsuite = view.findViewById(R.id.btnsuite);
        btnmension = view.findViewById(R.id.btnmension);
        btnlahore = view.findViewById(R.id.btnlahore);
        btnstars = view.findViewById(R.id.btnstars);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tosearch = edtsearch.getText().toString();
                Intent intent =new Intent(contexxt, searchMainActivity.class);
                intent.putExtra("search",tosearch);
                startActivity(intent);
            }
        });

        return view;
    }
}