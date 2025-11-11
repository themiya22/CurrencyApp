package com.example.currencyapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.example.currencyapp.NewsArticle;


public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_news);


        // fetchig data from api
        String url="https://newsdata.io/api/1/crypto?apikey=pub_4520359fa1ce45ac9534d824325f4ed2&q=foreign%20exchange&language=en";
        JsonObjectRequest request=new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                JSONArray responseArray = response.getJSONArray("results");
                List<NewsArticle> articles = new ArrayList<>();

                //adding to arraylist
                for(int i=0;responseArray.length()>i;i++){
                    JSONObject article = responseArray.getJSONObject(i);

                    String title = article.getString("title");
                    String pubDate = article.getString("pubDate");
                    String image_url = article.getString("image_url");
                    String source_url = article.getString("source_url");

                    NewsArticle newsArticle = new NewsArticle(i,title,pubDate,image_url,source_url);

                    articles.add(newsArticle);

                }
                RecyclerView recyclerView = findViewById(R.id.recyclerlbl);
                recyclerView.setLayoutManager(new LinearLayoutManager(NewsActivity.this));
                NewsAdapter n1 = new NewsAdapter(NewsActivity.this,articles);
                recyclerView.setAdapter(n1);

                }catch (Exception e){
                    e.printStackTrace();
                }


                Toast.makeText(NewsActivity.this, " API request successful", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NewsActivity.this, "ERROR: API request failed", Toast.LENGTH_SHORT).show();

                    }
                });
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(request);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}