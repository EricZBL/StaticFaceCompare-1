package com.hzgc.compare.file;

import com.hzgc.compare.Config;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class FileStreamManager {
    private static FileStreamManager fileStreamManager;
    private int serviceId = Config.SERVICE_ID;
    private int serviceNum = Config.SERVICE_NUM;
    private String path;
    private List<BufferedReader> Readers;
    private Map<String, BufferedWriter> cityToWriter;

    private FileStreamManager(){
        Readers = new ArrayList<>();
        cityToWriter = new HashMap<>();
        path = Config.FILE_PATH;
        init();
    }

    private void init(){
        File rootPath = new File(path);
        if(!rootPath.isDirectory()){
            log.error("The path " + path + " is not exist.");
            System.exit(1);
        }

        String[] fileNames = rootPath.list();
        if(fileNames == null || fileNames.length == 0){
            log.error("There is no file int the path " + path);
            System.exit(1);
        }

        for(String fileName : fileNames){
            String city = fileName.split(".")[0];
            if(city.hashCode() % serviceNum == serviceId){
                File file = new File(fileName);
                if(!file.isFile()){
                    continue;
                }
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                    Readers.add(reader);
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
                    cityToWriter.put(city, writer);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                    continue;
                }

            }
        }
    }

    public static FileStreamManager getInstanse(){
        if(fileStreamManager == null){
            fileStreamManager = new FileStreamManager();
        }
        return fileStreamManager;
    }

    public List<BufferedReader> getReaders(){
        return Readers;
    }

    public void removeReader(){
        try {
            for(BufferedReader reader : Readers) {
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        Readers = null;
    }

    public BufferedWriter getWriter(String city) {
        BufferedWriter writer = cityToWriter.get(city);
        if(writer == null){
            File cityFile = new File(path + File.separator + city + ".txt");
            if(!cityFile.isFile()){
                try {
                    cityFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                }
            }
            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cityFile, true)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
            cityToWriter.put(city, writer);
        }
        return writer;
    }
}
