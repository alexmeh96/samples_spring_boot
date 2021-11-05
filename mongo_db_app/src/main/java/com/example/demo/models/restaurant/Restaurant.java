package com.example.demo.models.restaurant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "restaurants", language = "russian")
public class Restaurant {
    @Id
    private String id;

    @TextIndexed(weight=10)
    private String name;
    private String address;
    private String img;
    private Float rating;
    @TextIndexed(weight=5)
    private String describe;
    private Location location;
    @TextIndexed(weight=2)
    private String[] kitchens;
    private int avgPrice;
    private int countMeals;
    private int minKkal;

    public void toRestaurant(RestaurantDTO restaurantDTO) {
        this.name = restaurantDTO.getName();
        this.address = restaurantDTO.getAddress();
        this.img = restaurantDTO.getImg();
        this.kitchens = restaurantDTO.getKitchens();
        if (restaurantDTO.getRating().equals("")) {
            this.rating = 0f;
        } else {
            this.rating = Float.parseFloat(restaurantDTO.getRating());
        }
        this.describe = restaurantDTO.getDescribe();
        Double[] locate = restaurantDTO.getLocate();
        double t = locate[0];
        locate[0] = locate[1];
        locate[1] = t;
        this.location = new Location();
        this.location.setCoordinates(locate);
        this.countMeals = restaurantDTO.getMeals().size();

        if (this.countMeals != 0) {
            int sum = 0;
            for (MealDTO meal: restaurantDTO.getMeals()) {
                sum += meal.getPrice();
            }
            this.avgPrice = sum / this.countMeals;
        }

        int minKkal = 100000;
        for (MealDTO meal : restaurantDTO.getMeals()) {
            if (meal.getKkal() != 0 && meal.getKkal()< minKkal) {
                minKkal = meal.getKkal();
            }
        }
        if (minKkal == 100000) {
            this.minKkal = 0;
        } else {
            this.minKkal = minKkal;
        }

    }
}