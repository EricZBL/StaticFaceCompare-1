package com.hzgc.manage.vo;

import com.hzgc.jniface.PictureData;
import com.hzgc.manage.entity.CapturedPicture;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SingleSearchResult implements Serializable {
    private String searchId;
//    private List<PictureData> pictureDatas;
    private int total;
    private List<PersonVO> personVOS;

}
