package com.hzgc.compare.file;

import com.hzgc.compare.Config;
import com.hzgc.jniface.FaceUtil;
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
    private List<String> filePaths;
    private BufferedWriter editWriter;
    private String eaditLogPath;

    private FileStreamManager(){
        Readers = new ArrayList<>();
        cityToWriter = new HashMap<>();
        filePaths = new ArrayList<>();
        path = Config.FILE_PATH;
        eaditLogPath = path + File.separator + Config.EADIT_LOG;
        init();
    }

    private void init(){
        log.info("Load streams");
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

        File editLog = new File(eaditLogPath);
        if(!editLog.isFile()){
            log.info("Edit log is not exit, create it.");
            try {
                editLog.createNewFile();
                editWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(editLog, true)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(String fileName : fileNames){
            if(!fileName.endsWith("txt")){
                continue;
            }
            String city = fileName.split("\\.")[0];
            if(city.hashCode() % serviceNum == serviceId){
                File file = new File(rootPath, fileName);
                if(!file.isFile()){
                    continue;
                }
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                    Readers.add(reader);
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
                    cityToWriter.put(city, writer);
                    filePaths.add(path + File.separator + fileName);
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

    public BufferedWriter getEditWriter() {
        return editWriter;
    }

    public List<String> getFilePathes() {
        return filePaths;
    }

    public void reLoadStreamWrite(){
        log.info("ReLoad Writer Streams");
        HashMap<String, BufferedWriter> temp = new HashMap<>();
        for(Map.Entry<String, BufferedWriter> writerEntry : cityToWriter.entrySet()){
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(path + File.separator + writerEntry.getKey() + ".txt", true)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            temp.put(writerEntry.getKey(), writer);
        }

        cityToWriter = temp;
    }
}
