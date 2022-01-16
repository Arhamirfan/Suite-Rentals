package com.example.suiterentals.homeScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.suiterentals.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    FirebaseAuth mauth;
    SharedPreferences sharedPreferences;
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
    }


    public void logoutDetails()
    {
        mauth = FirebaseAuth.getInstance();
        FirebaseUser user = mauth.getCurrentUser();
        sharedPreferences = getSharedPreferences("loggedinuser",0);
        SharedPreferences.Editor editoor = sharedPreferences.edit();
        String svedmail= sharedPreferences.getString("email",null);
        Toast.makeText(HomeActivity.this, "Logging out user: \n DB: "+user.getDisplayName() + "\n SP : "+ svedmail, Toast.LENGTH_SHORT).show();
        mauth.signOut();

        editoor.clear();
        editoor.apply();
    }
}