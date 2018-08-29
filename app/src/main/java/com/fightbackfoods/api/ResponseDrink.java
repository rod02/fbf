package com.fightbackfoods.api;

import com.fightbackfoods.model.Article;
import com.fightbackfoods.model.Drink;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseDrink extends Response {


    @SerializedName("user_drinks")
    private List<Drink> drinks;

    public List<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<Drink> drinks) {
        this.drinks = drinks;
    }

    public boolean isSuccessful(){
        try {
            return getSuccess().equalsIgnoreCase("true");
        }catch (NullPointerException e){
            e.printStackTrace();
            return super.isSuccessful();
        }
    }

    public static class Units extends Response{


        @SerializedName("units")
        private List<Drink.Units> units;

        public List<Drink.Units> getUnits() {
            return units;
        }

        public void setUnits(List<Drink.Units> units) {
            this.units = units;
        }
    }

}
