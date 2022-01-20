package com.example.suiterentals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.suiterentals.Model.Product;
import com.example.suiterentals.homeScreen.HomeActivity;
import com.example.suiterentals.loginScreens.loginsMainActivity;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mauth;
    SharedPreferences sharedPreferences,sp;
    @Override
    protected void onStart() {
        super.onStart();

      //FirebaseUser user = mauth.getCurrentUser();
//        if(user != null)
//        {
//            Toast.makeText(MainActivity.this, "Firebase user logged in is: "+user.getEmail(), Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(MainActivity.this, HomeActivity.class));
//            mauth = FirebaseAuth.getInstance();
//            finish();
//        }
    }

//    @Override
//    protected void onDestroy() {
//        sharedPreferences = getApplicationContext().getSharedPreferences("loggedinuser",MODE_PRIVATE);
//        SharedPreferences.Editor editoor = sharedPreferences.edit();
//        editoor.clear();
//        editoor.apply();
//        super.onDestroy();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        try
        {
            this.getSupportActionBar().hide();

            sharedPreferences = getSharedPreferences("loggedinuser", Context.MODE_PRIVATE);
            //sp = getSharedPreferences("userloginerror",Context.MODE_PRIVATE);
            //logging in even after error because the email in shared preference is present so applying SP as a check
            //String loginerror = sp.getString("error","");
            String name = sharedPreferences.getString("email","");
            mauth = FirebaseAuth.getInstance();
            FirebaseUser user = mauth.getCurrentUser();
            String uid = user.getUid();

            if(!uid.isEmpty())
            {
                Toast.makeText(this, "user session: SB:"+name+ "\nDB:"+ uid, Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(MainActivity.this,HomeActivity.class);

                startActivity(intent);
                finish();
//            if(!(loginerror.isEmpty()))
//            {
//                Toast.makeText(this, loginerror, Toast.LENGTH_SHORT).show();
//                Toast.makeText(this, "Error in wrong credentials of login", Toast.LENGTH_SHORT).show();
//            } else {
//                if(sharedPreferences.contains("email"))
//                {
//
//                } else{
//                    Toast.makeText(this, "No user found in SP", Toast.LENGTH_SHORT).show();
////            startActivity(new Intent(MainActivity.this,loginsMainActivity.class));
////            finish();
//                }
//            }
            }
            else{
                Toast.makeText(this, "No user in DB", Toast.LENGTH_SHORT).show();
            }
        }
        catch (NullPointerException e){
            Toast.makeText(MainActivity.this, "error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void movetoLoginActivity(View view) {
        Intent intent = new Intent(MainActivity.this, loginsMainActivity.class);
        startActivity(intent);
        finish();
    }


}