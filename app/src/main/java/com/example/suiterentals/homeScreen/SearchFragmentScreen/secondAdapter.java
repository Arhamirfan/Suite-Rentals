package com.example.suiterentals.homeScreen.SearchFragmentScreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suiterentals.Model.Product;
import com.example.suiterentals.R;
import com.example.suiterentals.homeScreen.HomeFragmentScreens.DownloadImageTask;
import com.example.suiterentals.homeScreen.HomeFragmentScreens.myAdapter;

import java.util.ArrayList;

public class secondAdapter extends RecyclerView.Adapter<secondAdapter.myViewHolder2>{

    private ArrayList<Product> productlist;

    public secondAdapter(ArrayList<Product> productlist) {
        this.productlist = productlist;
    }

    @NonNull
    @Override
    public myViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alldatalist,parent,false);
        return new myViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder2 holder, int position) {
        Product product = productlist.get(position);
        new DownloadImageTask(holder.imageView).execute(product.getAddress());
        holder.title.setText(product.getSuitetitle());
        holder.latitude.setText(product.getLatitude()+",");
        holder.longitude.setText(product.getLongitude());
        holder.price.setText("$ "+product.getPrice());
    }

    @Override
    public int getItemCount() {
        return productlist.size();
    }

    public class myViewHolder2 extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title,latitude,longitude,price;
        public myViewHolder2(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.rvtitle);
            imageView= itemView.findViewById(R.id.rvimage);
            latitude= itemView.findViewById(R.id.rvlatitide);
            longitude= itemView.findViewById(R.id.rvlongitude);
            price= itemView.findViewById(R.id.rvprice);
        }
    }
}
