package com.hzgc.utils;

import lombok.Data;

import java.util.List;

@Data
public class PageUtils<T> {

    private int number;

    private int size;

    private  long totalElements;

    private List<T> content;
}
