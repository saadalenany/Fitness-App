package com.example.root.fitnessapp.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by root on 13/06/17.
 */

public class Ingredient implements Serializable{

    String ingredient_name;
    int quantity , calories;

    public Ingredient() {
    }

    public Ingredient(String ingredient_name, int quantity, int calories) {
        this.ingredient_name = ingredient_name;
        this.quantity = quantity;
        this.calories = calories;
    }

    public String getIngredient_name() {
        return ingredient_name;
    }

    public void setIngredient_name(String ingredient_name) {
        this.ingredient_name = ingredient_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "ingredient_name='" + ingredient_name + '\'' +
                ", quantity=" + quantity +
                ", calories=" + calories +
                '}';
    }
}
