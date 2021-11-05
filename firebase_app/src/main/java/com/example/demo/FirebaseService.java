package com.example.demo;

import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Objects;

@Service
public class FirebaseService {

    @Value("${value.firebase.bucketName}")
    private String bucketName;

    @Value("${value.fileUpload.name}")
    private String uploadFilename;

    public String uploadFile() throws IOException {
        File file = new File(uploadFilename);
        String mimeType = "image/png";
        Path filePath = file.toPath();

        String objectName = generateFileName(file.getName());
        Bucket bucket = StorageClient.getInstance().bucket();
        Storage storage = bucket.getStorage();

        BlobId blobId = BlobId.of(bucketName, objectName);

        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(mimeType).build();
        System.out.println(blobInfo);

        Blob blob = storage.create(blobInfo, Files.readAllBytes(filePath));
//      Blob blob = storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));

        return blob.getName();
    }

    public void downloadFile(String filename) throws Exception {
        Bucket bucket = StorageClient.getInstance().bucket();
        Storage storage = bucket.getStorage();

        Blob blob = storage.get(BlobId.of(bucketName, filename));

        blob.downloadTo(Paths.get("./downloadFile.docx"));

        System.out.println("Success");

//        ReadChannel reader = blob.reader();
//        InputStream inputStream = Channels.newInputStream(reader);
//
//        byte[] content = null;
//
//        content = IOUtils.toByteArray(inputStream);
//
//        final ByteArrayResource byteArrayResource = new ByteArrayResource(content);


    }


    private String generateFileName(String filename) {
        return new Date().getTime() + "-" + Objects.requireNonNull(filename).replace(" ", "_");
    }

}
