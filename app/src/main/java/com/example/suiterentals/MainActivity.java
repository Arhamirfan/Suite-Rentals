package com.example.suiterentals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.suiterentals.Model.FirebaseData;
import com.example.suiterentals.homeScreen.HomeActivity;
import com.example.suiterentals.loginScreens.loginsMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mauth;
    SharedPreferences sharedPreferences;
    @Override
    protected void onStart() {
        super.onStart();
//        FirebaseUser user = mauth.getCurrentUser();
        sharedPreferences = getSharedPreferences("loggedinuser", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("email"))
        {
            String name = sharedPreferences.getString("email","");
            //Toast.makeText(this, "user session: "+ name, Toast.LENGTH_SHORT).show();
            mauth = FirebaseAuth.getInstance();
            FirebaseUser user = mauth.getCurrentUser();
            Toast.makeText(this, "user session: DB:"+ user.getUid() + "\n SB: "+ name, Toast.LENGTH_SHORT).show();
            constants constant = new constants();
            constant.fb = new FirebaseData(user.getUid(), user.getDisplayName(),user.getEmail());
            constant.fb.setId(user.getUid());
            constant.fb.setName(user.getDisplayName());
            constant.fb.setEmail(user.getEmail());
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
            finish();
        } else{
            Toast.makeText(this, "No user found", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(MainActivity.this,loginsMainActivity.class));
//            finish();
        }
//        if(user != null)
//        {
//            Toast.makeText(MainActivity.this, "Firebase user logged in is: "+user.getEmail(), Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(MainActivity.this, HomeActivity.class));
//            mauth = FirebaseAuth.getInstance();
//            finish();
//        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
    }

    public void movetoLoginActivity(View view) {
        Intent intent = new Intent(MainActivity.this, loginsMainActivity.class);
        startActivity(intent);
    }
}