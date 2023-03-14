package com.example.nav_bird11;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private ArrayList<BirdsData> imageList;
    private Context context;

    public ImageAdapter(ArrayList<BirdsData> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
    }


    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ViewHolder holder, int position) {
        // loading the images from the position

            Picasso.get().load(imageList.get(position).getImage()).into(holder.imageView);
            holder.title.setText(imageList.get(position).getTitle());


        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DescriptionActivity.class).putExtra("id",imageList.get(position).getId()).putExtra("name",imageList.get(position).getTitle()).putExtra("url",imageList.get(position).getImage());
                context.startActivity(intent);



            }
        });



    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.item);
            title=itemView.findViewById(R.id.title);
        }
    }
}
