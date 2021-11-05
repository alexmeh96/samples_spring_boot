package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class FirebaseController {

    @Autowired
    private FirebaseService firebaseService;


    @GetMapping("/upload-file")
    private ResponseEntity<?> uploadFile() throws IOException {

        String filename = firebaseService.uploadFile();
        return ResponseEntity.ok(filename);
    }

    @GetMapping("/download-file")
    private ResponseEntity<?> downloadFile(@RequestParam(required = false) String filename) throws Exception {

        if (filename != null) {
            firebaseService.downloadFile(filename);
            return ResponseEntity.ok("download success");
        } else {
            return ResponseEntity.ok("добавте в параметре запроса filename название файла!");
        }
    }
}
