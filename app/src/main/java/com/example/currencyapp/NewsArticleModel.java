package com.example.currencyapp;

public class NewsArticleModel {
    private String title;
    private String pubDate;
    private String image_url;
    private String link;

    public NewsArticleModel( String title, String pubDate, String image_url, String link) {

        this.title = title;
        this.pubDate = pubDate;
        this.image_url = image_url;
        this.link =link;
    }



    public String getTitle() {
        return title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getLink() {
        return link;
    }
}

