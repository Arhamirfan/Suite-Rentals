package com.example.suiterentals.homeScreen.SearchFragmentScreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.suiterentals.Model.Product;
import com.example.suiterentals.R;
import com.example.suiterentals.homeScreen.HomeFragmentScreens.myAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class searchMainActivity extends AppCompatActivity {

    RecyclerView myrecyclerview,rvtype2;
    ArrayList<Product> productList = new ArrayList<Product>();
    ArrayList<Product> finalproductlist = new ArrayList<Product>();
    ArrayList<Product> productListtype2 = new ArrayList<Product>();
    secondAdapter adapter,adaptertype2;
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
        adapter = new secondAdapter(productListtype2);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        myrecyclerview.setLayoutManager(layoutManager);
        myrecyclerview.setItemAnimator(new DefaultItemAnimator());
        //type 2:
        //TODO: watch searching on youtube..
    }


    //type 2: get by title
    private void getbytitle(String searchitem) {
        productListtype2.clear();
        db.collection("Products").whereEqualTo("suitetitle", searchitem)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            System.err.println("Listen failed:" + e);
                            return;
                        }
                        //listUsers = new ArrayList<User>();

                        for (DocumentSnapshot doc : snapshots) {
                            Product product = doc.toObject(Product.class);
                            productListtype2.add(product);
                        }
                        if(productListtype2.isEmpty())
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
        productListtype2.clear();
        db.collection("Products").whereEqualTo("price", searchitem)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            System.err.println("Listen failed:" + e);
                            return;
                        }
                        //listUsers = new ArrayList<User>();

                        for (DocumentSnapshot doc : snapshots) {
                            Product product = doc.toObject(Product.class);
                            productListtype2.add(product);
                        }
                        myrecyclerview.setAdapter(adapter);
                        pd.cancel();
                        //updateListUsers(productListtype2);
                    }
                });
    }
}