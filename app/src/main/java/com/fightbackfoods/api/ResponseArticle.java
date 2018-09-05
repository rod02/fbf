package com.fightbackfoods.api;

import com.fightbackfoods.model.Article;
import com.fightbackfoods.model.EducationItem;
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

    public static class Category extends Response{
        @SerializedName("categories")
        private List<EducationItem> categories;

        public List<EducationItem> getCategories() {
            return categories;
        }

        public void setCategories(List<EducationItem> categories) {
            this.categories = categories;
        }
    }

}
