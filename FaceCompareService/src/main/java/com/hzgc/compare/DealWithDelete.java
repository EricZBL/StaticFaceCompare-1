package com.hzgc.compare;

import com.hzgc.compare.cache.FeatureCache;
import com.hzgc.compare.common.Pair;
import com.hzgc.compare.file.FileManager;
import com.hzgc.compare.file.FileStreamManager;
import com.hzgc.jniface.FaceUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DealWithDelete {
    private String path = Config.FILE_PATH;
    private String eaditLogPath = path + File.separator + Config.EADIT_LOG;
    private List<String> edits = new ArrayList<>();
    private List<List<Pair<String, byte[]>>> list = new ArrayList<>();
    private ProcessBuilder builder = new ProcessBuilder();

    public void loadData(){
        readEdits();
        if(edits.size() == 0 || Config.DEAL_WITH_DELETE != 0){
            log.info("There is no delete option in the log");
            log.info("Load mate data");
            FileManager fileManager = new FileManager();
            fileManager.loadData();
            return;
        }
        log.info("There delete options in the log");
        log.info("Deal With it");
        List<String> filePathes = FileStreamManager.getInstanse().getFilePathes();
        for(String filPath : filePathes){
            reLoad(filPath);
        }
        dealWithFile(filePathes);
        rePush();

        FileStreamManager.getInstanse().reLoadStreamWrite();
    }

    private void readEdits(){
        log.info("Load edit log");
        File eaditLog = new File(eaditLogPath);
        if(!eaditLog.isFile()){
            return;
        }
        try {
            BufferedReader logReader = new BufferedReader(new InputStreamReader(new FileInputStream(eaditLogPath)));
            String line;
            while ((line = logReader.readLine()) != null){
                edits.add(line);
            }
            logReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reLoad(String file){
        log.info("Reload data from file " + file);
        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            try {
                List<Pair<String, byte[]>> datas = new ArrayList<>();
                list.add(datas);
                String line2;
                while ((line2 = fileReader.readLine()) != null){
                    String[] s = line2.split("_");
                    if(!edits.contains(s[0])) {
                        datas.add(new Pair<>(s[0], FaceUtil.base64Str2BitFeature(s[1])));
                    }
                }
                fileReader.close();

                BufferedWriter fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file + "bac", false)));
                int index = 0;
                for(Pair<String, byte[]> data : datas){
                    String info = data.getKey() + "_" + FaceUtil.bitFeautre2Base64Str(data.getValue());
                    fileWriter.write(info, 0, info.length());
                    fileWriter.newLine();
                    index ++;
                    if(index > 1000){
                        index = 0;
                        fileWriter.flush();
                    }
                }
                fileWriter.close();
            }catch (IOException e){
                e.printStackTrace();
                log.error(e.getMessage());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void rePush(){
        log.info("RePush data to memory cache");
        int featureMax = FeatureCache.getInstance().getFeatureSizeMax();
        byte[][] features = new byte[featureMax][32];
        String[] esIds = new String[featureMax];
        int index  = 0;
        for(List<Pair<String, byte[]>> datas : list){
            for(Pair<String, byte[]> data : datas){
                features[index] = data.getValue();
                esIds[index] = data.getKey();
                index ++;
            }
        }
        FeatureCache.getInstance().reLoadFeatures(features, esIds);
    }

    private void dealWithFile(List<String> filePaths){
        log.info("Delete file old, move new file to old");
        for(String filePath : filePaths){
            builder.command("rm", "-f", filePath);
            builder.command("mv", filePath + "bac", filePath);
            log.info("Command : rm -f " + filePath + "; mv " + filePath + "bac " + filePath);
        }
    }
}
