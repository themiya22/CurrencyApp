package com.example.currencyapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_news);


        //Fetchig data from api using volley library
        //Refference 01: written based on a youtube tutorial : https://www.youtube.com/watch?v=MgEsNXwj0lY)

        String api_url="https://newsdata.io/api/1/crypto?apikey=pub_4520359fa1ce45ac9534d824325f4ed2&q=foreign%20exchange&language=en";
        JsonObjectRequest request=new JsonObjectRequest(api_url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                List<NewsArticleModel> articles = new ArrayList<>();

                //Adding response into array list
                try{
                    JSONArray responseArray = response.getJSONArray("results");

                    for(int i=0;responseArray.length()>i;i++){
                        JSONObject article = responseArray.getJSONObject(i);

                        String title = article.getString("title");
                        String pubDate = article.getString("pubDate");
                        String image_url = article.getString("image_url");
                        String source_url = article.getString("source_url");

                        NewsArticleModel newsArticle = new NewsArticleModel(title,pubDate,image_url,source_url);
                        articles.add(newsArticle);

                    }
                    Toast.makeText(NewsActivity.this, " API request successful", Toast.LENGTH_SHORT).show();


                }catch (Exception e){
                    System.out.println("Exception while parsing jason into arraylist");
                    e.printStackTrace();
                }

                RecyclerView recyclerView = findViewById(R.id.recyclerlbl);
                recyclerView.setLayoutManager(new LinearLayoutManager(NewsActivity.this));
                NewsAdapter A1 = new NewsAdapter(NewsActivity.this,articles);
                recyclerView.setAdapter(A1);

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