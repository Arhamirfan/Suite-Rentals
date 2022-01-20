package com.example.suiterentals.homeScreen.SearchFragmentScreen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.suiterentals.Model.Suite;
import com.example.suiterentals.R;
import com.example.suiterentals.homeScreen.HomeFragmentScreens.myAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class searchMainActivity extends AppCompatActivity {

    RecyclerView myrecyclerview,rvtype2;
    ArrayList<Suite> suiteList = new ArrayList<Suite>();
    ArrayList<Suite> finalproductlist = new ArrayList<Suite>();
    ArrayList<Suite> suiteListtype2 = new ArrayList<Suite>();
    myAdapter adapter;
    ProgressDialog pd;
    String toSearchName;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_main);
        this.getSupportActionBar().hide();
        toSearchName = getIntent().getStringExtra("search");

        pd = new ProgressDialog(this);
        myrecyclerview = findViewById(R.id.recyclerview2);
        //Toast.makeText(searchMainActivity.this, "got text: "+toSearchName, Toast.LENGTH_SHORT).show();
        pd.setTitle("Getting Data");
        pd.show();

        getbytitle(toSearchName);
        adapter = new myAdapter(suiteListtype2,getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        myrecyclerview.setLayoutManager(layoutManager);
        myrecyclerview.setItemAnimator(new DefaultItemAnimator());
        //type 2:
        //TODO: watch searching on youtube..
    }


    //type 2: get by title
    private void getbytitle(String searchitem) {
        suiteListtype2.clear();
        db.collection("Suite").whereEqualTo("suitetitle", searchitem)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            System.err.println("Listen failed:" + e);
                            return;
                        }
                        //listUsers = new ArrayList<User>();

                        for (DocumentSnapshot doc : snapshots) {
                            Suite suite = doc.toObject(Suite.class);
                            suiteListtype2.add(suite);
                        }
                        if(suiteListtype2.isEmpty())
                        {
                            getbyprice(searchitem);
                        }else {
                            myrecyclerview.setAdapter(adapter);
                            pd.cancel();
                        }
                    }
                });
    }

    private void getbyprice(String searchitem) {
        suiteListtype2.clear();
        db.collection("Suite").whereEqualTo("price", searchitem)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            System.err.println("Listen failed:" + e);
                            return;
                        }
                        //listUsers = new ArrayList<User>();

                        for (DocumentSnapshot doc : snapshots) {
                            Suite suite = doc.toObject(Suite.class);
                            suiteListtype2.add(suite);
                        }
                        myrecyclerview.setAdapter(adapter);
                        pd.cancel();
                        //updateListUsers(productListtype2);
                    }
                });
    }
}