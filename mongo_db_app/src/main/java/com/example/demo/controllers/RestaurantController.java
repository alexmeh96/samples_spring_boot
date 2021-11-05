package com.example.demo.controllers;

import com.example.demo.models.restaurant.*;
import com.example.demo.repositories.RestaurantRepo;
import com.example.demo.services.RestaurantService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Value("${value.file-for-db.filename}")
    private String fileForDb;

    @GetMapping("/add-data")
    public ResponseEntity<?> add() {

        if(restaurantService.restaurantsExist()) {
            return ResponseEntity.ok("Restaurants already yet!");
        }

        Gson gson = new Gson();
        RestaurantDTO[] restaurants = new RestaurantDTO[0];

        try(FileReader reader = new FileReader(fileForDb)) {
            restaurants = gson.fromJson(reader, RestaurantDTO[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (RestaurantDTO restaurantDTO : restaurants) {
            Restaurant restaurant = new Restaurant();
            restaurant.toRestaurant(restaurantDTO);
            String id = restaurantService.addRestaurant(restaurant).getId();
            Location location = restaurant.getLocation();

            for (MealDTO mealDTO: restaurantDTO.getMeals()) {
                Meal meal = new Meal();
                meal.toMeal(mealDTO);
                meal.setRestaurantId(id);
                meal.setLocation(location);
                restaurantService.addMeal(meal);
            }
        }
        return ResponseEntity.ok("Success add");
    }

    @GetMapping("/get-restaurants")
    public ResponseEntity<?> getRestaurant(@RequestParam int page, @RequestParam int size) {

        List<Restaurant> restaurants = restaurantService.getRestaurant(page, size);
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/get-meals")
    public ResponseEntity<?> getMeal(@RequestParam int page, @RequestParam int size) {

        List<Meal> meals = restaurantService.getMeal(page, size);
        return ResponseEntity.ok(meals);
    }

    @GetMapping("/search-full-text-meals")
    public ResponseEntity<?> searchFullTextMeals(@RequestParam String text) {

        List<Meal> meals = restaurantService.searchFullTextMeal(text);
        return ResponseEntity.ok(meals);
    }

    @GetMapping("/search-regx-meals")
    public ResponseEntity<?> searchRegxMeals(@RequestParam String text,
                                             @RequestParam int page,
                                             @RequestParam int size
    ) {
        List<Meal> meals = restaurantService.searchRegxMeal(text, page, size);
        return ResponseEntity.ok(meals);
    }

}
