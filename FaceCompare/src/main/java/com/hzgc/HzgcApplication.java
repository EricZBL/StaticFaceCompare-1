package com.hzgc;

import com.hzgc.jniface.FaceFunction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HzgcApplication {

    public static void main(String[] args) {

//        FaceFunction.init();
        SpringApplication.run(HzgcApplication.class, args);

    }
}
