package com.example.suiterentals.homeScreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suiterentals.MainActivity;
import com.example.suiterentals.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ProfileFragment extends Fragment {

    View view;
//    FirebaseAuth mauth;
    Context contexxt;
//    FirebaseUser users;
    TextView txtname,txtemail;
//    String uid,eemail;
    FloatingActionButton profile,dashboard,location,cart,feeds,logout;
//    SharedPreferences sharedPreferences;
    HomeActivity homeActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        contexxt = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        //context = container.getContext();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        FirebaseAuth auth;
//        auth = FirebaseAuth.getInstance();
//        FirebaseUser user = auth.getCurrentUser();
//        uid = user.getUid();
//        eemail = user.getEmail();
        //Toast.makeText(contexxt, "id after getting : "+uid, Toast.LENGTH_SHORT).show();
//        String name = user.getDisplayName();
//        String email = user.getEmail();
//        if(!name.isEmpty()){
        homeActivity = (HomeActivity) getActivity();
            txtname = view.findViewById(R.id.txtName);
            txtemail = view.findViewById(R.id.txtEmail);
            txtname.setText(homeActivity.fbemail);
            txtemail.setText(homeActivity.uid);
//        }
//        else{
//            Toast.makeText(getActivity(), "Getting No Data", Toast.LENGTH_SHORT).show();
//        }
        profile= (FloatingActionButton) view.findViewById(R.id.btnProfile);
        dashboard= (FloatingActionButton) view.findViewById(R.id.btnDashboard);
        location= (FloatingActionButton) view.findViewById(R.id.btnLocation);
        cart= (FloatingActionButton) view.findViewById(R.id.btnCart);
        feeds= (FloatingActionButton) view.findViewById(R.id.btnFeed);
        logout = (FloatingActionButton) view.findViewById(R.id.btnLogout);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "This is profile", Toast.LENGTH_SHORT).show();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutDetails();
            }
        });
    }

    public void logoutDetails()
    {
        //sharedPreferences = contexxt.getSharedPreferences("loggedinuser",0);
        //SharedPreferences.Editor editoor = sharedPreferences.edit();
        //String svedmail= sharedPreferences.getString("email",null);
        //No data is showing under DB session..
        //TODO: not getting name from database
        //Log.d("TAG", "DB name: "+ users.getDisplayName());
        //Toast.makeText(getActivity(), "Logging out user: \n DB: "+users.getDisplayName() + "\n SP : "+ svedmail, Toast.LENGTH_SHORT).show();

        try {
            homeActivity.getMauth().signOut();
            homeActivity.getEditor().clear();
            homeActivity.getEditor().apply();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }catch (NullPointerException e)
        {
            Toast.makeText(contexxt, "Profile logout error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}