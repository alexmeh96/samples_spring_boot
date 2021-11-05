package com.example.demo;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Value("${value.firebase.filename}")
    private String filename;

    @Value("${value.firebase.bucketName}")
    private String bucketName;

    @Primary
    @Bean
    public void firebaseInit() throws IOException {

        FileInputStream serviceAccount = new FileInputStream(filename);

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket(bucketName)
                .build();
        FirebaseApp.initializeApp(options);

    }

}