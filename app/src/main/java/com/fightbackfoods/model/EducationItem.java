package com.fightbackfoods.model;

import com.fightbackfoods.R;
import com.fightbackfoods.interfaces.Item;

import java.util.ArrayList;
import java.util.List;

public class EducationItem implements Item {

    private String id;
    private String name;
    private String description;
    private int total;
    private String imageUrl;
    private int imageRes;

    public EducationItem(String name, String description, int total, int imageRes) {
        this.name = name;
        this.description = description;
        this.total = total;
        this.imageRes = imageRes;
    }

    public static List<EducationItem> dummy(){
        List<EducationItem> list = new ArrayList<>();
        list.add(new EducationItem("Food and Nutrition", "Your best asset is your health", 402, R.drawable.raw_educ1 ));
        list.add(new EducationItem("Mind Body and Soul", "Peace of mind is the answer", 233, R.drawable.raw_educ2 ));
        list.add(new EducationItem("Community", "Learn and live with harmony", 142, R.drawable.raw_educ3 ));
        list.add(new EducationItem("Documentaries", "Interview with survivors", 213, R.drawable.raw_educ4 ));
        list.add(new EducationItem("Success Stories", "Story of the how to stay healthy", 652, R.drawable.raw_educ5 ));

        return list;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }
}
