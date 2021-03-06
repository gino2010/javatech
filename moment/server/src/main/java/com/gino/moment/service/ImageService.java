package com.gino.moment.service;

import com.gino.moment.entity.ImageEntity;
import com.gino.moment.model.UserImage;
import com.gino.moment.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gino
 * Created on 2018/8/21
 */
@Service
public class ImageService {
    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer saveImage(Integer id, String userId, byte[] image) {
        ImageEntity entity;
        if (id == null) {
            entity = new ImageEntity();
        } else {
            entity = imageRepository.getOne(id);
        }
        entity.setUserId(userId);
        if (image != null && image.length != 0) {
            entity.setImage(image);
        }
        imageRepository.save(entity);
        return entity.getId();
    }

    public List<ImageEntity> getImages() {
        return imageRepository.findAll();
    }

    public List<ImageEntity> getImagesByUser(String userId) {
        return imageRepository.getByUserId(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteImageById(Integer id) {
        imageRepository.deleteById(id);
        return true;
    }

    public List<UserImage> getUserImages() {
        List<UserImage> userImageList = new ArrayList<>();
        List<String> userIds = imageRepository.getUserIds();
        for (String user : userIds) {
            List<Integer> idsByUserId = imageRepository.getIdsByUserId(user);
            userImageList.add(new UserImage(user, idsByUserId));
        }
        return userImageList;
    }

    public ImageEntity getImageById(Integer id) {
        return imageRepository.getOne(id);
    }

    public List<String> getUserId() {
        return imageRepository.getUserIds();
    }
}
