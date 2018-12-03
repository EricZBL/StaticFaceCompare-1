package com.hzgc;

import com.hzgc.jniface.FaceFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class HzgcApplication {

    public static void main(String[] args) {
        FaceFunction.init();
        SpringApplication.run(HzgcApplication.class, args);

    }
}
