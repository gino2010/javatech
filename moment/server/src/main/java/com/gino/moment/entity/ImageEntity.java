package com.gino.moment.entity;

import sun.misc.BASE64Encoder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author gino
 * Created on 2018/8/21
 */
@Entity
@Table(name = "t_image", schema = "moment")
public class ImageEntity implements Serializable {

    private static final long serialVersionUID = 2760338615044661369L;

    private Integer id;
    private String userId;
    private byte[] image;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "image")
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageEntity that = (ImageEntity) o;
        return id.equals(that.id) &&
                Objects.equals(userId, that.userId) &&
                Arrays.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, userId);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }

    public String generateBase64Image() {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(getImage());
    }
}
