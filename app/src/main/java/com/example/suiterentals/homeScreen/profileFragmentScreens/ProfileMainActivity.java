package com.example.suiterentals.homeScreen.profileFragmentScreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suiterentals.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileMainActivity extends AppCompatActivity {

    TextView tvid,tvmail,tvfname,tvaddres;
    FloatingActionButton fabhome;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);
        this.getSupportActionBar().hide();
        Intent intent = getIntent();

        String email = intent.getStringExtra("email");
        String uid = intent.getStringExtra("uid");

        tvid = findViewById(R.id.Id);
        tvmail= findViewById(R.id.mail);
        tvfname = findViewById(R.id.fname);
        tvaddres= findViewById(R.id.addres);
        fabhome = findViewById(R.id.fabhome);

        tvid.setText(uid);
        tvid.setFocusable(false);
        tvid.setEnabled(false);
        tvmail.setText(email);
        tvmail.setFocusable(false);
        tvmail.setEnabled(false);
        fabhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String naam = tvfname.getText().toString();
                String jaga = tvaddres.getText().toString();
                if(TextUtils.isEmpty(naam))
                {
                    Toast.makeText(ProfileMainActivity.this, "Name is empty", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(jaga)){
                    Toast.makeText(ProfileMainActivity.this, "Address entered is empty", Toast.LENGTH_SHORT).show();
                }else{
                    db.collection("users").document(uid)
                            .update("name", naam,"address",jaga).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ProfileMainActivity.this, "Data updated", Toast.LENGTH_SHORT).show();
                                //Snackbar.make(ProfileMainActivity.this,"Data Updated.",Snackbar.LENGTH_SHORT).show();
                                tvfname.setText("");
                                tvaddres.setText("");
                            }else{

                            }
                        }
                    });
                }
            }
        });
    }
}