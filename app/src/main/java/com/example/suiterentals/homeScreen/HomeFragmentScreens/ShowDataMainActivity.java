package com.example.suiterentals.homeScreen.HomeFragmentScreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.suiterentals.Model.Product;
import com.example.suiterentals.Model.productImages;
import com.example.suiterentals.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ShowDataMainActivity extends AppCompatActivity {

    RecyclerView myrecyclerview;
    ArrayList<Product> productList = new ArrayList<Product>();
//    ArrayList<productImages> imagelist = new ArrayList<productImages>();
    ArrayList<String> imagelist;
    myAdapter adapter;
    ProgressDialog pd;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data_main);
        this.getSupportActionBar().hide();

        pd = new ProgressDialog(this);
        myrecyclerview = findViewById(R.id.recyclerview);
        imagelist = new ArrayList<>();
//        Product product;
//        product = new Product("123","Home","This is testing","401","0","401","401","nothing");
//        productList.add(product);
//        product = new Product("456","Suite","This is testing","401","0","401","401","nothing");
//        productList.add(product);
//        imagelist.add("https://firebasestorage.googleapis.com/v0/b/suite-rentals.appspot.com/o/allproducts%2F36162407-e4db-4f2a-b628-d4fd514e3022?alt=media&token=a7c59843-157f-42bc-b224-37021daf8859");
//        imagelist.add("https://firebasestorage.googleapis.com/v0/b/suite-rentals.appspot.com/o/allproducts%2F36162407-e4db-4f2a-b628-d4fd514e3022?alt=media&token=a7c59843-157f-42bc-b224-37021daf8859");
        pd.setTitle("Getting Data");
        pd.show();
        //TODO: Get data from Database
        //getData();
        getListItems();
        //getListImages();
        //TODO: save data in list
//        adapter = new myAdapter(productList,imagelist,getApplicationContext());
        adapter = new myAdapter(productList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        myrecyclerview.setLayoutManager(layoutManager);
        myrecyclerview.setItemAnimator(new DefaultItemAnimator());

    }

//    private void getData() {
//        //get data and save as:
////        Product product;
////        product = new Product("","","","","","","","");
////        productList.add(product);
////
//        db.collection("Products")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                //Log.d("TAG", document.getId() + " => " + document.getData().get("price").toString());
//                                String price = document.getData().get("price").toString();
//                            }
//                        } else {
//                            Log.w("TAG", "Error getting documents.", task.getException());
//                        }
//                    }
//                });
//    }

    private void getListItems() {
        db.collection("Products").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Empty List of Products.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            List<Product> types = queryDocumentSnapshots.toObjects(Product.class);
                            productList.addAll(types);
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

//    private void getListImages()
//    {
//        StorageReference refer = FirebaseStorage.getInstance().getReference("allproducts/");
//
//        refer.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
//            @Override
//            public void onSuccess(ListResult listResult) {
//                for(StorageReference file:listResult.getItems()){
//                    file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            imagelist.add(uri.toString());
//
//                            //Log.e("Itemvalue",uri.toString());
//                        }
//                    }).addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            //recyclerView.setAdapter(adapter);
//                            //progressBar.setVisibility(View.GONE);
//                            myrecyclerview.setAdapter(adapter);
//                            pd.cancel();
//
//                        }
//                    });
//                }
//            }
//        });


        //Toast.makeText(getApplicationContext(), imagelist.get(0), Toast.LENGTH_SHORT).show();
//        refer.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//                // Data for "images/island.jpg" is returns, use this as needed
//                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle any errors
//            }
//        });
    //}


}