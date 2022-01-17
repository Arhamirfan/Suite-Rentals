package com.example.suiterentals.loginScreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.suiterentals.Model.FirebaseData;
import com.example.suiterentals.R;
import com.example.suiterentals.homeScreen.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginsMainActivity extends AppCompatActivity {

    FirebaseAuth mauth;
    EditText Email,password;
    SharedPreferences sharedPreferences;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
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
        sharedPreferences = getSharedPreferences("loggedinuser", Context.MODE_PRIVATE);

    }

    public void loginAccount(View view) {
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
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email",email);
            editor.apply();
            mauth=FirebaseAuth.getInstance();
            mauth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(loginsMainActivity.this, "Successfully logged in!", Toast.LENGTH_SHORT).show();
                        FirebaseAuth mAuth;
                        FirebaseUser User;
                        mAuth = FirebaseAuth.getInstance();
                        User = mAuth.getCurrentUser();
                        Toast.makeText(getApplicationContext(), "uid: "+User.getUid(), Toast.LENGTH_SHORT).show();
                        FirebaseData fb = new FirebaseData(User.getUid(),User.getDisplayName(),User.getEmail());
                        startActivity(new Intent(loginsMainActivity.this,HomeActivity.class));
                    }
                    else{
                        Toast.makeText(loginsMainActivity.this, "Failed to login with : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        finish();
    }

    public void createAnAccount(View view) {
        Intent intent = new Intent(loginsMainActivity.this,createAccountActivity.class);
        startActivity(intent);
    }
}