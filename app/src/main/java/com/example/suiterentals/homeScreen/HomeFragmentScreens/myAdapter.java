package com.example.suiterentals.homeScreen.HomeFragmentScreens;

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

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.myViewHolder>{

    private ArrayList<Suite> suitelist;
    private Context context;

    public myAdapter(ArrayList<Suite> suitelist, Context context) {
        this.suitelist = suitelist;
        this.context = context;
    }

    public myAdapter(ArrayList<Suite> suitelist) {
        this.suitelist = suitelist;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alldatalist,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Suite suite = suitelist.get(position);
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
        new DownloadImageTask(holder.imageView).execute(suite.getAddress());
        holder.title.setText(suite.getSuitetitle());
        holder.latitude.setText(suite.getLatitude()+",");
        holder.longitude.setText(suite.getLongitude());
        holder.price.setText("$ "+ suite.getPrice());
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context,BookProductMainActivity.class);
//                intent.putExtra("productlist",suite);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });
        holder.btndetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,BookProductMainActivity.class);
                intent.putExtra("productlist",suite);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return suitelist.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title,latitude,longitude,price;
        Button btndetail;
        public myViewHolder(@NonNull View itemView) {
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
