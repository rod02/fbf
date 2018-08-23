package com.fightbackfoods.model;

import com.fightbackfoods.R;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LifestyleGoal extends Goal {
    @SerializedName("lifestyletracker_goal_id")
    private String id;

    public LifestyleGoal(String title, String date, String points, boolean star) {
        super();
        setTitle(title);
        setCreatedAt(date);
        setPoints(points);
        setStar(star);
    }

    boolean star;

    public boolean isStar() {
        return star;
    }

    public void setStar(boolean star) {
        this.star = star;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return getDescription();
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    public void setId(String id) {
        this.id = id;
    }


    public static List<LifestyleGoal> dummy(){
        List<LifestyleGoal> list = new ArrayList<>();
        list.add(new LifestyleGoal("Watered the Plants", "Mon Jan 23", "1:10",false));
        list.add(new LifestyleGoal("Walked 1 mile", "Mon Jan 23", "1 mile",false));
        list.add(new LifestyleGoal("Sat on a Yoga Ball", "Mon Jan 23", "20 min",true));

        return list;
    }
}
