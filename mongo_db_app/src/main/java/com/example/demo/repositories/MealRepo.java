package com.example.demo.repositories;

import com.example.demo.models.restaurant.Meal;
import com.example.demo.models.restaurant.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MealRepo extends MongoRepository<Meal, String> {
    Page<Meal> findAll(Pageable pageable);

}
