package com.gino.moment.controller;

import com.gino.moment.model.UserImage;
import com.gino.moment.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author gino
 * Created on 2018/8/21
 */
@RestController
@RequestMapping("api/")
public class ApiController {
    private final ImageService imageService;

    @Autowired
    public ApiController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("user_image")
    public List<UserImage> getUserImages() {
        return imageService.getUserImages();
    }

    @GetMapping("image/{id}")
    public String getImage(@PathVariable("id") Integer id) {
        return imageService.getImageById(id).generateBase64Image();
    }
}
