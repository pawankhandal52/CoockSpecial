/*
 * Copyright (C) 2018 The Android Nanodegree Project made under Udacity Nanodegree Course
 * Author Pawan Kumar Sharma
 * All Rights Reserved
 */
package com.pawankhandal52.coockspecial.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
 * Simple POJO for a Ingredients List
 */
public class Ingredient implements Parcelable {
    
    public final static Parcelable.Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        
        @SuppressWarnings({
                "unchecked"
        })
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }
        
        public Ingredient[] newArray(int size) {
            return (new Ingredient[size]);
        }
        
    };
    @SerializedName("quantity")
    @Expose
    private Double quantity;
    @SerializedName("measure")
    @Expose
    private String measure;
    @SerializedName("ingredient")
    @Expose
    private String ingredient;
    
    Ingredient(Parcel in) {
        this.quantity = ((Double) in.readValue((int.class.getClassLoader())));
        this.measure = ((String) in.readValue((String.class.getClassLoader())));
        this.ingredient = ((String) in.readValue((String.class.getClassLoader())));
    }
    
    public Ingredient() {
    }
    
    public Double getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
    
    public String getMeasure() {
        return measure;
    }
    
    public void setMeasure(String measure) {
        this.measure = measure;
    }
    
    public String getIngredient() {
        return ingredient;
    }
    
    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
    
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(quantity);
        dest.writeValue(measure);
        dest.writeValue(ingredient);
    }
    
    public int describeContents() {
        return 0;
    }
    
}
