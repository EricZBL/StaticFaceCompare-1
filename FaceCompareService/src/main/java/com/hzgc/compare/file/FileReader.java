package com.hzgc.compare.file;

import com.hzgc.compare.cache.FeatureCache;
import com.hzgc.compare.common.Pair;
import com.hzgc.jniface.FaceUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FileReader implements Runnable{
    private FeatureCache featureCache = FeatureCache.getInstance();
    private boolean end = false;
    private List<BufferedReader> list = new ArrayList<>();

    void addReader(BufferedReader reader){
        list.add(reader);
    }

    boolean isEnd(){
        return end;
    }

    @Override
    public void run() {
        long count = 0L;
        for(BufferedReader reader : list){
            try {
                List<Pair<String, byte[]>> data = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null){
                    String[] s = line.split("_");
                    if(s.length != 2){
                        continue;
                    }
                    data.add(new Pair<>(s[0], FaceUtil.base64Str2BitFeature(s[1])));
                    count ++;
                }
                featureCache.addFeatures(data);
            }catch (Exception e){
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
        log.info("The num of Records Loaded is : " + count);
        end = true;
    }
}
