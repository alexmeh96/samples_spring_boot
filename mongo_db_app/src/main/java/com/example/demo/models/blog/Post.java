package com.example.demo.models.blog;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.TextScore;

import java.util.List;

@Data
@NoArgsConstructor
@Document
public class Post {

    @Id
    private String id;

    @TextIndexed(weight=5)
    private String title;

    @TextIndexed(weight=1)
    private String body;

    @TextIndexed(weight=3)
    private List<String> tags;

    @TextIndexed(weight=4)
    private String category;

    @TextScore
    private Float score;

    public Post(String title, String body, List<String> tags, String category) {
        this.title = title;
        this.body = body;
        this.tags = tags;
        this.category = category;
    }
}
