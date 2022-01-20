package com.example.suiterentals.loginScreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.suiterentals.R;
import com.example.suiterentals.homeScreen.HomeActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginsMainActivity extends AppCompatActivity {

    FirebaseAuth mauth;
    EditText Email,password;
    SharedPreferences sharedPreferences,sharedpreferences;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser user = mauth.getCurrentUser();
//        if(user == null)
//        {
//            startActivity(new Intent(loginsMainActivity.this, HomeActivity.class));
//            mauth = FirebaseAuth.getInstance();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logins_main);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        Email = findViewById(R.id.editemail);
        password = findViewById(R.id.editpassword);
        progressDialog = new ProgressDialog(this);
        sharedPreferences = getSharedPreferences("loggedinuser", Context.MODE_PRIVATE);

    }

    public void loginAccount(View view) {
        progressDialog.setMessage("User has signed in");
        progressDialog.show();
        String email = Email.getText().toString();
        String pass = password.getText().toString();
        if(TextUtils.isEmpty(email) || !(email.matches(emailPattern)))
        {
            Toast.makeText(loginsMainActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
            Email.setError("Email cannot be empty");
            Email.requestFocus();
        }
        else if(pass.length() < 8){
            Toast.makeText(loginsMainActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
            password.setError("Password is invalid");
            password.requestFocus();
        }
        else
        {
            try {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email",email);
                editor.apply();
                mauth=FirebaseAuth.getInstance();
                mauth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(loginsMainActivity.this, "Successfully logged in!", Toast.LENGTH_SHORT).show();
                        //FirebaseData fb = new FirebaseData(User.getUid(),User.getDisplayName(),User.getEmail());
//                        sharedPreferences = getSharedPreferences("userloginerror",0);
//                        SharedPreferences.Editor editoor = sharedPreferences.edit();
//                        editoor.clear();
//                        editoor.apply();
                        progressDialog.cancel();
                        Intent intent =new Intent(loginsMainActivity.this,HomeActivity.class);

                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.cancel();
                        Toast.makeText(loginsMainActivity.this, "Failed to login with : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        //logging in even after error because the email in shared preference is present so applying SP as a check
//                        sharedpreferences = getSharedPreferences("userloginerror", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editoor = sharedpreferences.edit();
//                        editoor.putString("error",e.getMessage());
//                        editoor.apply();
                    }
                });
            }catch (NullPointerException e)
            {
                Toast.makeText(loginsMainActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }

    public void createAnAccount(View view) {
        Intent intent = new Intent(loginsMainActivity.this,createAccountActivity.class);
        startActivity(intent);
    }
}