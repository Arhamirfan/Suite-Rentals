package com.example.suiterentals.homeScreen.SearchFragmentScreen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suiterentals.Model.Suite;
import com.example.suiterentals.R;
import com.example.suiterentals.homeScreen.HomeFragmentScreens.BookProductMainActivity;
import com.example.suiterentals.homeScreen.HomeFragmentScreens.DownloadImageTask;

import java.util.ArrayList;

public class secondAdapter extends RecyclerView.Adapter<secondAdapter.myViewHolder2>{

    private ArrayList<Suite> suitelist;
    Context contexxt;

    public secondAdapter(ArrayList<Suite> suitelist, Context contexxt) {
        this.suitelist = suitelist;
        this.contexxt = contexxt;
    }

    public secondAdapter(ArrayList<Suite> suitelist) {
        this.suitelist = suitelist;
    }

    @NonNull
    @Override
    public myViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alldatalist,parent,false);
        return new myViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder2 holder, int position) {
        Suite suite = suitelist.get(position);
        new DownloadImageTask(holder.imageView).execute(suite.getAddress());
        holder.title.setText(suite.getSuitetitle());
        holder.latitude.setText(suite.getLatitude()+",");
        holder.longitude.setText(suite.getLongitude());
        holder.price.setText("$ "+ suite.getPrice());
        holder.btndetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(contexxt, BookProductMainActivity.class);
                intent.putExtra("productlist",suite);
                contexxt.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return suitelist.size();
    }

    public class myViewHolder2 extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title,latitude,longitude,price;
        Button btndetail;
        public myViewHolder2(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.rvtitle);
            imageView= itemView.findViewById(R.id.rvimage);
            latitude= itemView.findViewById(R.id.rvlatitide);
            longitude= itemView.findViewById(R.id.rvlongitude);
            price= itemView.findViewById(R.id.rvprice);
            btndetail = itemView.findViewById(R.id.btndetails);
        }
    }
}
