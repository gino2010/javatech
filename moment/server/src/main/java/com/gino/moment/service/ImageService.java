package com.gino.moment.service;

import com.gino.moment.entity.ImageEntity;
import com.gino.moment.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<Integer> getIds() {
        return imageRepository.getIds();
    }

    public List<ImageEntity> getImages() {
        return imageRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteImageById(Integer id) {
        imageRepository.deleteById(id);
        return true;
    }

    public List<Integer> getIdByUserId(String userId) {
        return imageRepository.getIdsByUserId(userId);
    }

    public List<String> getUserIds() {
        return imageRepository.getUserIds();
    }

    public ImageEntity getImageById(Integer id) {
        return imageRepository.getOne(id);
    }
}
