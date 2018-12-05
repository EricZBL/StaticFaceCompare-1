package com.hzgc.jniface;


import java.io.Serializable;

public class PictureData implements Serializable {
    //图片ID
    protected String imageID;

    //图片二进制数据
    protected byte[] imageData;

    //人脸特征对象,包括特征值和人脸属性
    private FaceAttribute feature;

    private int[] image_coordinate;

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public FaceAttribute getFeature() {
        return feature;
    }

    public void setFeature(FaceAttribute feature) {
        this.feature = feature;
    }

    public int[] getImage_coordinate() {
        return image_coordinate;
    }

    public void setImage_coordinate(int[] image_coordinate) {
        this.image_coordinate = image_coordinate;
    }
}
