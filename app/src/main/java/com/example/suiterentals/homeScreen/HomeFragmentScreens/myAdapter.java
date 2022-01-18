package com.example.suiterentals.homeScreen.HomeFragmentScreens;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suiterentals.Model.Product;
import com.example.suiterentals.Model.productImages;
import com.example.suiterentals.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.myViewHolder>{

    private ArrayList<Product> productlist;
    private Context context;

    public myAdapter(ArrayList<Product> productlist,Context context) {
        this.productlist = productlist;
        this.context = context;
    }

    public myAdapter(ArrayList<Product> productlist) {
        this.productlist = productlist;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alldatalist,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Product product = productlist.get(position);
        //TODO: get Bitmap image and set the image
        //Glide.with(holder.itemView.getContext()).load(imageList.get(position)).into(holder.imageView);
        //Glide.with(holder.itemView.getContext()).load(product.getAddress()).into(holder.imageView);
//        URL newurl = null;
//        try {
//            newurl = new URL(product.getAddress());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        Bitmap mIcon_val = null;
//        try {
//            mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //holder.imageView.setImageBitmap(mIcon_val);
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

    public class myViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title,latitude,longitude,price;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.rvtitle);
            imageView= itemView.findViewById(R.id.rvimage);
            latitude= itemView.findViewById(R.id.rvlatitide);
            longitude= itemView.findViewById(R.id.rvlongitude);
            price= itemView.findViewById(R.id.rvprice);
        }
    }
}
