package com.fightbackfoods.model;


import com.google.gson.annotations.SerializedName;

class FoodInfo {

    @SerializedName("name")
    private String name;

    @SerializedName("nutrients")
    private Nutrients nutrients;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Nutrients getNutrients() {
        return nutrients;
    }

    public void setNutrients(Nutrients nutrients) {
        this.nutrients = nutrients;
    }

    public static class Nutrients {



        @SerializedName("calories")
        private Nutrient calories;
        @SerializedName("protein")
        private Nutrient protein;
        @SerializedName("total_fat")
        private Nutrient totalFat;
        @SerializedName("carbohydrate")
        private Nutrient carbohydrate;

        @SerializedName("dietary_fiber")
        private Nutrient dietaryFiber;

        @SerializedName("calcium")
        private Nutrient calcium;
        @SerializedName("iron")
        private Nutrient iron;
        @SerializedName("potassium")
        private Nutrient potassium;


        @SerializedName("sodium")
        private Nutrient sodium;

        @SerializedName("vitamin_c")
        private Nutrient vitaminC;
        @SerializedName("vitamin_a")
        private Nutrient vitaminA;

        @SerializedName("saturated")
        private Nutrient saturatedFat;

        @SerializedName("cholesterol")
        private Nutrient cholesterol;
        @SerializedName("sugar")
        private Nutrient sugar;
        @SerializedName("trans")
        private Nutrient trans;

        public Nutrient getCalories() {
            return calories;
        }

        public void setCalories(Nutrient calories) {
            this.calories = calories;
        }

        public Nutrient getProtein() {
            return protein;
        }

        public void setProtein(Nutrient protein) {
            this.protein = protein;
        }

        public Nutrient getTotalFat() {
            return totalFat;
        }

        public void setTotalFat(Nutrient totalFat) {
            this.totalFat = totalFat;
        }

        public Nutrient getCarbohydrate() {
            return carbohydrate;
        }

        public void setCarbohydrate(Nutrient carbohydrate) {
            this.carbohydrate = carbohydrate;
        }

        public Nutrient getDietaryFiber() {
            return dietaryFiber;
        }

        public void setDietaryFiber(Nutrient dietaryFiber) {
            this.dietaryFiber = dietaryFiber;
        }

        public Nutrient getCalcium() {
            return calcium;
        }

        public void setCalcium(Nutrient calcium) {
            this.calcium = calcium;
        }

        public Nutrient getIron() {
            return iron;
        }

        public void setIron(Nutrient iron) {
            this.iron = iron;
        }

        public Nutrient getPotassium() {
            return potassium;
        }

        public void setPotassium(Nutrient potassium) {
            this.potassium = potassium;
        }

        public Nutrient getSodium() {
            return sodium;
        }

        public void setSodium(Nutrient sodium) {
            this.sodium = sodium;
        }

        public Nutrient getVitaminC() {
            return vitaminC;
        }

        public void setVitaminC(Nutrient vitaminC) {
            this.vitaminC = vitaminC;
        }

        public Nutrient getVitaminA() {
            return vitaminA;
        }

        public void setVitaminA(Nutrient vitaminA) {
            this.vitaminA = vitaminA;
        }

        public Nutrient getSaturatedFat() {
            return saturatedFat;
        }

        public void setSaturatedFat(Nutrient saturatedFat) {
            this.saturatedFat = saturatedFat;
        }

        public Nutrient getCholesterol() {
            return cholesterol;
        }

        public void setCholesterol(Nutrient cholesterol) {
            this.cholesterol = cholesterol;
        }

        public Nutrient getSugar() {
            return sugar;
        }

        public void setSugar(Nutrient sugar) {
            this.sugar = sugar;
        }

        public Nutrient getTrans() {
            return trans;
        }

        public void setTrans(Nutrient trans) {
            this.trans = trans;
        }

/*
    'Calories',
            204 => 'Total Fat',
            606 => 'Saturated Fat',
            605 => 'Trans',
            601 => 'Cholesterol',
            307 => 'Sodium',
            306 => 'Potassium',
            205 => 'Total Carbs',
            291 => 'Dietary Fiber',
            269 => 'Sugar',
            203 => 'Protein',
            318 => 'Vitamin A',
            401 => 'Vitamin C',
            301 => 'Calcium',
            303 => 'Iron*/




    }
    public static class Nutrient extends com.fightbackfoods.model.Nutrients{};
}
