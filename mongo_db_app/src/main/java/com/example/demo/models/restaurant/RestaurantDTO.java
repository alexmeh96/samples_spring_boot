package com.example.demo.models.restaurant;

import lombok.Data;

import java.util.List;

@Data
public class RestaurantDTO {

    private String name;
    private String address;
    private String img;
    private String rating;
    private String describe;
    private Double[] locate;
    private String[] kitchens;
    private List<MealDTO> meals;


}
