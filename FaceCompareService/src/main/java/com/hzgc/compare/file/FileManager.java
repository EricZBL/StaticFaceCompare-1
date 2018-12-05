package com.hzgc.compare.file;

import com.hzgc.compare.Config;
import com.hzgc.jniface.FaceUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class FileManager {
    private int serviceId = Config.SERVICE_ID;
    private int serviceNum = Config.SERVICE_NUM;
    private int readFilePerThread = Config.FILES_PER_THREAD;
    private FileStreamManager fileStreamManager = FileStreamManager.getInstanse();
    private ExecutorService pool;

    public FileManager(){
        pool = Executors.newFixedThreadPool(10);;
    }

    public void loadData(){
        long start = System.currentTimeMillis();
        List<BufferedReader> readerList = fileStreamManager.getReaders();
        List<FileReader> list = new ArrayList<>();
        FileReader fileReader = new FileReader();
        list.add(fileReader);
        int num = 0;
        for(BufferedReader reader : readerList){
            if(num < readFilePerThread){
                fileReader.addReader(reader);
                num ++;
            } else {
                fileReader = new FileReader();
                list.add(fileReader);
                fileReader.addReader(reader);
                num = 1;
            }
        }

        if(list.size() == 0){
            log.info("There is no file to load.");
            return;
        }

        for(FileReader fileReader1 : list){
            pool.submit(fileReader1);
        }

        while (true){
            boolean flug = true;
            for(FileReader fileReader1: list){
                flug = fileReader1.isEnd() && flug;
            }
            if(flug){
                break;
            }
        }
        pool.shutdown();
        log.info("The time used to load record is : " + (System.currentTimeMillis() - start));
        fileStreamManager.removeReader();
    }

    public void write(String idCard, String esId, byte[] feature){
        String city = idCard.substring(0, 4);
        if(city.hashCode() % serviceNum == serviceId){
            String info = esId + "_" + FaceUtil.bitFeautre2Base64Str(feature);
            log.info("Write the data " + idCard + "To File.");
            BufferedWriter writer = fileStreamManager.getWriter(city);
            try {
                writer.write(info, 0, info.length());
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
    }
}
