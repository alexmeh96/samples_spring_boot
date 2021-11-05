package com.example.demo.services;

import com.example.demo.models.restaurant.Meal;
import com.example.demo.models.restaurant.Restaurant;
import com.example.demo.repositories.MealRepo;
import com.example.demo.repositories.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepo restaurantRepo;
    @Autowired
    private MealRepo mealRepo;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepo.save(restaurant);
    }

    public Meal addMeal(Meal meal) {
        return mealRepo.save(meal);
    }

    public boolean restaurantsExist() {
        return restaurantRepo.count() != 0;
    }

    public List<Restaurant> getRestaurant(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return restaurantRepo.findAll(paging).getContent();
    }

    public List<Meal> getMeal(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return mealRepo.findAll(paging).getContent();
    }

    public List<Meal> searchFullTextMeal(String text) {
        TextCriteria criteria = TextCriteria
//                .forDefaultLanguage()
                .forLanguage("russian")
                .matching(text);

        Query query = TextQuery.queryText(criteria).sortByScore().limit(10);

        List<Meal> meals = mongoTemplate.find(query, Meal.class);
        return meals;
    }

    public List<Meal> searchRegxMeal(String text, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));

        Query query = new Query();

        Criteria criteria = new Criteria().orOperator(
                Criteria.where("name").regex(text, "i"),
                Criteria.where("category").regex(text, "i"),
                Criteria.where("description").regex(text, "i")
        );

        query.addCriteria(criteria).skip(pageable.getPageSize() * pageable.getPageNumber())
                .limit(pageable.getPageSize());

        List<Meal> meals = mongoTemplate.find(query, Meal.class);

        return meals;
    }

}