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
        //for type 1, type 2 is setting recycler view data in its function
//        getSearchItems();
        getbyprice(toSearchName);
        adapter = new secondAdapter(productListtype2);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        myrecyclerview.setLayoutManager(layoutManager);
        myrecyclerview.setItemAnimator(new DefaultItemAnimator());
        //type 2:
        //TODO: watch searching on youtube..
    }

    //type 1: (Error in loop getting no data found) so trying type 2
    private void getSearchItems() {
        productList.clear();
        finalproductlist.clear();
        db.collection("Products").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        productList.clear();
                        if (queryDocumentSnapshots.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Empty List of Products.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            List<Product> types = queryDocumentSnapshots.toObjects(Product.class);
                            productList.addAll(types);
                            for(int i=0;i<productList.size();i++)
                            {
                                if(productList.get(i).getSuitetitle() == toSearchName)
                                {
                                    Toast.makeText(getApplicationContext(), "Found Data in Title", Toast.LENGTH_SHORT).show();
                                    finalproductlist.add(productList.get(i));

                                } else if(productList.get(i).getPrice() == toSearchName)
                                {
                                    Toast.makeText(getApplicationContext(), "Found Data in Price", Toast.LENGTH_SHORT).show();
                                    finalproductlist.add(productList.get(i));

                                }else if(productList.get(i).getRooms() == toSearchName)
                                {
                                    Toast.makeText(getApplicationContext(), "Found Data in Rooms", Toast.LENGTH_SHORT).show();
                                    finalproductlist.add(productList.get(i));

                                } else
                                {
                                    Toast.makeText(getApplicationContext(), "No data found ☻", Toast.LENGTH_SHORT).show();
                                }
                            }
                            myrecyclerview.setAdapter(adapter);
                            pd.cancel();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    //type 2: get by title
    private void getbytitle(String searchitem) {
        productListtype2.clear();
        db.collection("users").whereEqualTo("suitetitle", searchitem)
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
                        updateListUsers(productListtype2);
                    }
                });
    }

    private void getbyprice(String searchitem) {
        productListtype2.clear();
        db.collection("users").whereEqualTo("price", searchitem)
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

    private void updateListUsers(ArrayList<Product> listUsers) {

        // Sort the list by date
        Collections.sort(listUsers, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                int res = -1;
                if (o1.getSuitetitle().length() > (o2.getSuitetitle().length())) {
                    res = 1;
                } else if(Integer.valueOf(o1.getPrice()) > (Integer.valueOf(o2.getPrice())) )
                {
                    res =1;
                }
                return res;
            }
        });

//        adaptertype2 = new secondAdapter(listUsers);
////        rvtype2.setNestedScrollingEnabled(false);
//        rvtype2.setAdapter(adaptertype2);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        rvtype2.setLayoutManager(layoutManager);
//        adaptertype2.notifyDataSetChanged();
    }
}