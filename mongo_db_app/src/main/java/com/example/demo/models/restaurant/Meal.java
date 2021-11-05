package com.example.demo.models.restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "meals", language = "russian")
public class Meal {
    @Id
    private String id;

    @TextIndexed(weight=10)
    private String name;
    private String img;
    private String restaurant;
    private int price;
    @TextIndexed(weight=3)
    private String description;
    @TextIndexed(weight=6)
    private String category;
    private int mass;
    private int kkal;
    private int fat;
    private int prot;
    private int carb;
    private String restaurantId;
    private Location location;

    private String date;

    public void toMeal(MealDTO mealDTO) {
        this.name = mealDTO.getName();
        this.img = mealDTO.getImg();
        this.restaurant =  mealDTO.getRestaurant();
        this.price =  mealDTO.getPrice();
        this.description =  mealDTO.getDescription();
        this.category =  mealDTO.getCategory();
        this.mass =  mealDTO.getMass();
        this.kkal =  mealDTO.getKkal();
        this.fat =  mealDTO.getFat();
        this.prot =  mealDTO.getProt();
        this.carb =  mealDTO.getCarb();
    }

}
