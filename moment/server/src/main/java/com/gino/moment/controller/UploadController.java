package com.gino.moment.controller;

import com.gino.moment.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

/**
 * @author gino
 * Created on 2018/8/21
 */
@Slf4j
@Controller
public class UploadController {
    private final ImageService imageService;

    @Autowired
    public UploadController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/")
    public String listUploadedImages(Model model) {
        model.addAttribute("entities", imageService.getImages());
        model.addAttribute("users", imageService.getUserId());
        model.addAttribute("current_user", "");
        return "upload";
    }

    @GetMapping("/{userId}")
    public String listUploadedImages(@PathVariable("userId") String userId, Model model) {
        model.addAttribute("entities", imageService.getImagesByUser(userId));
        model.addAttribute("users", imageService.getUserId());
        model.addAttribute("current_user", userId);
        return "upload";
    }

    @PostMapping("/")
    public String uploadImage(@RequestParam(value = "id", required = false) Integer id, @RequestParam(value = "user_id") String userId,
                              @RequestParam(value = "image", required = false) MultipartFile image, RedirectAttributes redirectAttributes) {
        upload(id, userId, image, redirectAttributes);
        return "redirect:/";
    }

    @PostMapping("/{userId}")
    public String uploadImage(@RequestParam(value = "id", required = false) Integer id,
                              @RequestParam(value = "user_id") String userId,
                              @RequestParam(value = "image", required = false) MultipartFile image,
                              @PathVariable("userId") String selectUserId,
                              RedirectAttributes redirectAttributes) {
        upload(id, userId, image, redirectAttributes);
        return "redirect:/" + selectUserId;
    }

    private void upload(Integer id, String userId, MultipartFile image, RedirectAttributes redirectAttributes) {
        try {
            Integer newId = imageService.saveImage(id, userId, image.getBytes());
            redirectAttributes.addFlashAttribute("message",
                    id == null ? String.format("Id:%s You successfully uploaded", newId) : String.format("Id:%s You successfully update", id));
        } catch (IOException e) {
            log.error("upload image error:{}", e.getMessage());
            redirectAttributes.addFlashAttribute("message",
                    id == null ? "You save new image failed" : String.format("You update image #%s failed", id));
        }
    }

    @PostMapping("/del/{id}")
    public String deleteImage(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        if (imageService.deleteImageById(id)) {
            redirectAttributes.addFlashAttribute("message",
                    String.format("You delete image #%s success", id));
        } else {
            redirectAttributes.addFlashAttribute("message",
                    String.format("You delete image #%s failed", id));
        }
        return "redirect:/";
    }
}
