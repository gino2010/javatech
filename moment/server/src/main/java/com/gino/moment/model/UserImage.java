package com.gino.moment.model;

import java.io.Serializable;
import java.util.List;

/**
 * User and Image
 *
 * @author gino
 * Created on 2018/8/25
 */
public class UserImage implements Serializable {

    private static final long serialVersionUID = 8084213432836436514L;

    private String userId;
    private List<Integer> imageIds;

    public UserImage(String userId, List<Integer> imageIds) {
        this.userId = userId;
        this.imageIds = imageIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Integer> getImageIds() {
        return imageIds;
    }

    public void setImageIds(List<Integer> imageIds) {
        this.imageIds = imageIds;
    }
}
