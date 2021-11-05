package com.example.demo.repositories;

import com.example.demo.models.restaurant.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RestaurantRepo extends MongoRepository<Restaurant, String> {
    Page<Restaurant> findAll(Pageable pageable);
}
