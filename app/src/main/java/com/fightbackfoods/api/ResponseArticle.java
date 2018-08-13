package com.fightbackfoods.api;

import com.fightbackfoods.model.Article;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseArticle extends Response {


    @SerializedName("articles")
    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

}
