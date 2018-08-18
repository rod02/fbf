package com.fightbackfoods.api;

import com.fightbackfoods.model.Article;
import com.fightbackfoods.model.Banner;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseBanner extends Response {


    @SerializedName("banners")
    private List<Banner> banners;


    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }
}
