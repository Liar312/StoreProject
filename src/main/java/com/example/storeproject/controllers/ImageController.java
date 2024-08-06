package com.example.storeproject.controllers;

import com.example.storeproject.models.Image;
import com.example.storeproject.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ImageController {
    private final ImageRepository imageRepository;

    private ResponseEntity<?> getImageByID(@RequestParam Long id){
        Image image = imageRepository.findById(id).orElse(null);
        return ResponseEntity.ok()
                .header("fileName", image.getOriginalName())
                .contentType(MediaType.valueOf(image.getContentType()))//mediaType.valueof используется для получения типа файла, ну мы туда кидаем наш контент тайп
                .body(new InputStreamResource(new ByteArrayResource(image.getBytes())));
    }
}
