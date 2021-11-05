package com.example.demo.models.restaurant;

import lombok.Data;

@Data
public class Location {
    private Double[] coordinates;
    private String type = "Point";
}