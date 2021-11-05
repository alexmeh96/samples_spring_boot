package com.example.demo.models.restaurant;

import lombok.Data;

@Data
public class MealDTO {
    private String name;
    private String img;
    private String restaurant;
    private int price;
    private String description;
    private String category;
    private int mass;
    private int kkal;
    private int fat;
    private int prot;
    private int carb;
}
