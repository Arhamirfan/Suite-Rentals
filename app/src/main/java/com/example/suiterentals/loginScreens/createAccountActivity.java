package com.example.suiterentals.loginScreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.suiterentals.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class createAccountActivity extends AppCompatActivity {

    EditText name,email,address,password;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mauth;
    ProgressDialog progressDialog;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        name = findViewById(R.id.edtname);
        email = findViewById(R.id.edtemail);
        address = findViewById(R.id.edtaddress);
        password = findViewById(R.id.edtpassword);
        progressDialog = new ProgressDialog(this);
    }

    public void registerAccount(View view) {
        progressDialog.setMessage("Creating User..");
        progressDialog.show();
        String id = UUID.randomUUID().toString();
        String namee = name.getText().toString();
        String emmail = email.getText().toString().trim();
        String adress = address.getText().toString();
        String pass = password.getText().toString().trim();

        if(TextUtils.isEmpty(emmail) || !(emmail.matches(emailPattern)))
        {
            Toast.makeText(createAccountActivity.this, "Fields are incorrect", Toast.LENGTH_SHORT).show();
            email.setError("Email is invalid");
            email.requestFocus();
            progressDialog.cancel();
        }
        else if(pass.length() < 8){
            Toast.makeText(createAccountActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
            password.setError("Password is invalid");
            password.requestFocus();
            progressDialog.cancel();
        }
        else{

            mauth=FirebaseAuth.getInstance();
            mauth.createUserWithEmailAndPassword(emmail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        FirebaseUser users = mauth.getCurrentUser();
                        String uid = users.getUid();
                        Toast.makeText(getApplicationContext(), "Adding id:"+uid, Toast.LENGTH_SHORT).show();
                        Map<String, Object> user = new HashMap<>();
                        user.put("id",uid);
                        user.put("name", namee);
                        user.put("email", emmail);
                        user.put("password", pass);
                        user.put("address", adress);
                        db.collection("users").document(uid).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                progressDialog.cancel();
                                Toast.makeText(createAccountActivity.this, "Successfully Added, login in again", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(createAccountActivity.this,loginsMainActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.cancel();
                                Toast.makeText(createAccountActivity.this, "Failed to add with exception: "+e.toString(), Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                    else {
                        progressDialog.cancel();
                        Toast.makeText(createAccountActivity.this, "Error in authentication :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}