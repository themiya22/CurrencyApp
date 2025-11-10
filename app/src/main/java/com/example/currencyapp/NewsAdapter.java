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
import com.example.currencyapp.NewsArticle;
import com.squareup.picasso.Picasso;
import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsArticle> articles;
    private Context context;

    public NewsAdapter(Context context, List<NewsArticle> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsArticle article = articles.get(position);
        holder.title.setText(article.getTitle());
        holder.date.setText(article.getPubDate());
        Picasso.get().load(article.getImage_url()).into(holder.image);

        //-----------------buged area--------------------------------
        holder.readMorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = article.getLink();
                if (url != null && !url.isEmpty()) {
                    // Create an Intent to open the URL in a browser
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(browserIntent);
                } else {
                    // Let the user know if there is no link
                    Toast.makeText(context, "Link not available", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView title, country, date;
        ImageView image;
        Button readMorebtn;


        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tittlelbl);
            date = itemView.findViewById(R.id.datelbl);
            image = itemView.findViewById(R.id.imageView);
            readMorebtn = itemView.findViewById(R.id.readmorebtn);
        }





    }
}
