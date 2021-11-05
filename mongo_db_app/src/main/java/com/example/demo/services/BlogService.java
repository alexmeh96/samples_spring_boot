package com.example.demo.services;

import com.example.demo.models.blog.Post;
import com.example.demo.models.person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Post> getAllPost() {
        List<Post> posts = mongoTemplate.findAll(Post.class);
        return posts;
    }

    public void addPost() {
        Post post1 = new Post("title1", "description1", List.of("teg1", "teg2"), "category1");
        Post post2 = new Post("title2", "description2", List.of("teg1", "teg2", "teg3"), "category2");
        Post post3 = new Post("title3", "description3", List.of("teg3", "teg4"), "category1");

        mongoTemplate.insertAll(List.of(post1, post2, post3));

    }

    public List<Post> fullTextSearch(String searchPhrase) {
        TextCriteria criteria = TextCriteria
                .forDefaultLanguage()
                .matchingPhrase(searchPhrase);

        Query query = TextQuery.queryText(criteria).sortByScore();

        List<Post> posts = mongoTemplate.find(query, Post.class);

        return posts;
    }

    public List<Post> findPostByAggregation(String category) {
        AggregationOperation match = Aggregation.match(Criteria.where("category").is(category));
        AggregationOperation sort = Aggregation.sort(Sort.Direction.ASC, "title");

        Aggregation aggregation = Aggregation.newAggregation(match, sort);

        List<Post> posts = mongoTemplate.aggregate(aggregation, mongoTemplate.getCollectionName(Post.class), Post.class).getMappedResults();

        return posts;
    }

    public List<Post> findPostWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").descending());

        Query query = new Query();
        query.skip(pageable.getPageSize() * pageable.getPageNumber())
                .limit(pageable.getPageSize());

        List<Post> posts = mongoTemplate.find(query, Post.class);
        long count = mongoTemplate.count(query.skip(-1).limit(-1), Post.class);
        Page<Post> resultPage = new PageImpl<>(posts, pageable, count);

        return resultPage.getContent();
    }

    public List<Post> getAllPostWithoutTags() {
        Query query = new Query();
        query.fields().exclude("tags");

        return mongoTemplate.find(query, Post.class);
    }

}
