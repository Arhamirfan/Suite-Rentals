package com.example.suiterentals.homeScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.suiterentals.Model.Suite;
import com.example.suiterentals.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    FirebaseAuth mauth;
    FirebaseUser user;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Suite> suiteList = new ArrayList<Suite>();
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public String uid,fbemail,spemail;
    //ProgressDialog pd;
//    @Override
//    protected void onStart() {
//        super.onStart();
//        sharedPreferences = getSharedPreferences("loggedinuser",Context.MODE_PRIVATE);
//        sp = getSharedPreferences("userloginerror",Context.MODE_PRIVATE);
//        //logging in even after error because the email in shared preference is present so applying SP as a check
//        String loginerror = sp.getString("error","");
//        if(!loginerror.isEmpty())
//        {
//            Toast.makeText(HomeActivity.this, loginerror, Toast.LENGTH_LONG).show();
//            SharedPreferences.Editor editoor = sharedPreferences.edit();
//            editoor.clear();
//            editoor.apply();
//            startActivity(new Intent(HomeActivity.this, MainActivity.class));
//            finish();
//        }
//    }

    @Override
    protected void onStart() {
        //pd = new ProgressDialog(this);
        mauth =FirebaseAuth.getInstance();
        user = mauth.getCurrentUser();
        uid = user.getUid();
        fbemail = user.getEmail();
        sharedPreferences = getApplicationContext().getSharedPreferences("loggedinuser",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        spemail= sharedPreferences.getString("email","");
        //pd.setTitle("Fetching Data..");
        //pd.show();
        getListItems();
        //productList = (ArrayList<Product>) getIntent().getSerializableExtra("productlist");
        //pd.cancel();
        Log.d("TAG", "onStart HomeActivity: loggedin User: "+uid);
        //Toast.makeText(getApplicationContext(), "User ID: "+uid, Toast.LENGTH_SHORT).show();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //NavController navController = Navigation.findNavController(this,  R.id.fragment);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.searchFragment, R.id.productFragment, R.id.profileFragment).build();
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
        }

        //for(int i=0;i<productList.size();i++){}

    }

    public FirebaseAuth getMauth() {
        return mauth;
    }

    public FirebaseFirestore getDb() {
        return db;
    }

    public ArrayList<Suite> getProductList() {
        return suiteList;
    }
    //    public FirebaseUser getUser() {
//        return user;
//    }
//
//    public SharedPreferences getSharedPreferences() {
//        return sharedPreferences;
//    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

//Get Data From DB by this:
    public void getListItems() {
        getDb().collection("Suite").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            Log.d("TAG", "onStart HomeActivity: Empty DB list: "+uid);
                            //Toast.makeText(getApplicationContext(), "Empty List of Suite.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            List<Suite> types = queryDocumentSnapshots.toObjects(Suite.class);
                            suiteList.addAll(types);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
            }
        });
    }
//
//    public void logoutDetails()
//    {
//        mauth = FirebaseAuth.getInstance();
//        FirebaseUser user = mauth.getCurrentUser();
//        sharedPreferences = getSharedPreferences("loggedinuser",0);
//        SharedPreferences.Editor editoor = sharedPreferences.edit();
//        String svedmail= sharedPreferences.getString("email",null);
//        Toast.makeText(HomeActivity.this, "Logging out user: \n DB: "+user.getDisplayName() + "\n SP : "+ svedmail, Toast.LENGTH_SHORT).show();
//        mauth.signOut();
//
//        editoor.clear();
//        editoor.apply();
//    }
}