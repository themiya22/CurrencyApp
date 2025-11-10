package com.example.currencyapp;

public class NewsArticle {
    private int article_id;
    private String title;
    private String pubDate;
    private String image_url;
    private String link;

    public NewsArticle(int article_id,String title, String pubDate, String image_url, String link) {
        this.article_id=article_id;
        this.title = title;
        this.pubDate = pubDate;
        this.image_url = image_url;
        this.link =link;
    }

    public int getArticle_id() {
        return article_id;
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

