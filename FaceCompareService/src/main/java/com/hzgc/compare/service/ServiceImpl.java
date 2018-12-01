package com.hzgc.compare.service;


import com.hzgc.common.CompareParam;
import com.hzgc.common.SearchResult;
import com.hzgc.common.Service;
import com.hzgc.common.UpdateParam;
import com.hzgc.common.rpc.client.result.AllReturn;
import com.hzgc.common.rpc.server.annotation.RpcService;
import com.hzgc.compare.Config;
import com.hzgc.compare.cache.FeatureCache;
import com.hzgc.compare.compare.CompareOnePerson;
import com.hzgc.compare.file.FileManager;
import com.hzgc.compare.file.FileStreamManager;
import com.hzgc.jniface.FaceUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
@RpcService(com.hzgc.common.Service.class)
public class ServiceImpl implements Service {
    private int serviceNum = Config.SERVICE_NUM;
    private int serviceId = Config.SERVICE_ID;
    @Override
    public AllReturn<SearchResult> retrievalOnePerson(CompareParam param) {
        if(param.getFeature() == null || param.getFeatureBit() == null){
            log.error("The feature and bit feature can not be null");
            return new AllReturn<>(new SearchResult());
        }
        CompareOnePerson compareOnePerson = new CompareOnePerson();
        SearchResult searchResult = compareOnePerson.compare(param.getFeatureBit(), param.getFeature(), param.getSim());
        return new AllReturn<>(searchResult);
    }

    @Override
    public AllReturn<Boolean> add(UpdateParam updateParam) {
        if(updateParam.getEsId() == null || updateParam.getIdCard() == null || updateParam.getBitFeature() == null){
            log.error("The esId , feature and idcard can not be null");
            return new AllReturn<>(false);
        }
        String city = updateParam.getIdCard().substring(0, 4);
        if(city.hashCode() % serviceNum == serviceId){
            log.info("Add data " + updateParam.getEsId());
            FileManager fileManager = new FileManager();
            byte[] bitFeature = FaceUtil.base64Str2BitFeature(updateParam.getBitFeature());
            fileManager.write(updateParam.getIdCard(), updateParam.getEsId(), bitFeature);
            FeatureCache.getInstance().addFeature(updateParam.getEsId(), bitFeature);
        }
        return new AllReturn<>(true);
    }

    @Override
    public AllReturn<Boolean> delete(UpdateParam updateParam) {
        if(updateParam.getEsId() == null || updateParam.getIdCard() == null){
            log.error("The es id and idcard can not be null");
            return new AllReturn<>(false);
        }
        String city = updateParam.getIdCard().substring(0, 4);
        if(city.hashCode() % serviceNum == serviceId){
            log.info("Delete data " + updateParam.getEsId());
            String path = Config.FILE_PATH  + File.separator + ".log";
            File logFile = new File(path);
            try {
                if(!logFile.exists() || !logFile.isFile()){
                    logFile.createNewFile();
                }
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFile, true)));
                writer.write(updateParam.getEsId(), 0, updateParam.getEsId().length());
                writer.newLine();
                writer.close();
                FeatureCache.getInstance().deleteFeature(updateParam.getEsId());
            } catch (IOException e) {
                e.printStackTrace();
                return new AllReturn<>(false);
            }
        }
        return new AllReturn<>(true);
    }

    @Override
    public AllReturn<String> test() throws InterruptedException {
        log.info("TEST ");
        return new AllReturn<>("response");
    }
}
