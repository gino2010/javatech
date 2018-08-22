package com.gino.moment.controller;

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

    @GetMapping("id")
    public List<Integer> getImageIds() {
        return imageService.getIds();
    }

    @GetMapping("id/{user_id}")
    public List<Integer> getImageIdsByUser(@PathVariable("user_id") String userId) {
        return imageService.getIdByUserId(userId);
    }

    @GetMapping("user_id")
    public List<String> getUserIds() {
        return imageService.getUserIds();
    }

    @GetMapping("image/{id}")
    public String getImage(@PathVariable("id") Integer id) {
        return imageService.getImageById(id).generateBase64Image();
    }
}
