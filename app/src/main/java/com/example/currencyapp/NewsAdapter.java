package com.example.currencyapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

//Reference 02: written based on the lab worksheets and a youtube tutorial : https://youtu.be/Mc0XT58A1Z4?si=-zYzgs2nY50-yxp-
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    Context context;
    List<NewsArticleModel> articles;

    public NewsAdapter(Context context, List<NewsArticleModel> articles){
        this.context= context;
        this.articles = articles;

    }


    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_layout,parent,false);
        return new NewsAdapter.NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {
        holder.tvTittle.setText(articles.get(position).getTitle());
        holder.tvDate.setText(articles.get(position).getPubDate());

        //using picasso to load image from url
        //Refference 03: https://www.geeksforgeeks.org/android/how-to-use-picasso-image-loader-library-in-android/
        String imgUrl = articles.get(position).getImage_url();
        Picasso.get().load(imgUrl).placeholder(R.drawable.alt_image).resize(320, 300).into(holder.imgView);



        holder.readMorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = articles.get(position).getLink();

                //safe guard for no source link responses
                if(link!=null){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    try{
                        context.startActivity(browserIntent);
                    }
                    catch (Exception e){
                        Toast.makeText(context, "Sorry no link to source", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(context, "Sorry no link to source", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView tvTittle,tvDate;
        ImageView imgView;
        Button readMorebtn;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTittle = itemView.findViewById(R.id.tittlelbl);
            tvDate = itemView.findViewById(R.id.datelbl);
            imgView = itemView.findViewById(R.id.imageView);
            readMorebtn = itemView.findViewById(R.id.readmorebtn);
        }
    }
}
