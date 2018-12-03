package com.hzgc.compare.cache;

import com.hzgc.compare.Config;
import com.hzgc.compare.common.Pair;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class FeatureCache {
    private static FeatureCache featureCache;
    private ReentrantLock writeLock = new ReentrantLock();
    private byte[][] featureArr;
    private String[] esIdArr;
    private static int FEA_SIZE_DEFAULT = Config.FEA_SIZE_DEFAULT;
    private int featureSize = 0;
    private int featureSizeMax = FEA_SIZE_DEFAULT;


    private void init(){
        featureArr = new byte[FEA_SIZE_DEFAULT][32];
        esIdArr = new String[FEA_SIZE_DEFAULT];
    }

    private FeatureCache(){
        init();
    }

    public static FeatureCache getInstance(){
        if(featureCache == null){
            featureCache = new FeatureCache();
        }
        return featureCache;
    }

    /**
     * 扩展缓存数组
     */
    private void expand(){
        featureSizeMax += FEA_SIZE_DEFAULT / 10;
        byte[][] newFeatureArr = new byte[featureSizeMax][32];
        System.arraycopy(featureArr, 0, newFeatureArr, 0, featureSize);
        String[] newEsIdArr = new String[featureSizeMax];
        System.arraycopy(esIdArr, 0, newEsIdArr, 0, featureSize);
        featureArr = newFeatureArr;
        esIdArr = newEsIdArr;
    }

    /**
     * 内存中增加一条数据
     * @param id
     * @param feature
     * @return
     */
    public boolean addFeature(String id, byte[] feature){
        writeLock.lock();
        try {
            if(featureSize >= featureSizeMax){
                expand();
            }
            featureArr[featureSize] = feature;
            esIdArr[featureSize] = id;
            featureSize ++;
            log.info("The size of cache is " + featureSize);
            return true;
        }finally {
            writeLock.unlock();
        }

    }

    /**
     * 删除一条数据
     * @param id
     * @return
     */
    public boolean deleteFeature(String id){
        writeLock.lock();
        try {
            long start = System.currentTimeMillis();
            int index = -1;
            for(int i  = 0 ; i < featureSize; i ++){
                if(id.equals(esIdArr[i])){
                    index = i;
                }
            }
            if(index == -1){
                log.error("There is no id in memory cache : " + id);
                return false;
            }

            String[] newIdsArr = new String[featureSizeMax];
            byte[][] newFeatureArr = new byte[featureSizeMax][32];
            System.arraycopy(esIdArr, 0, newIdsArr, 0, index);
            System.arraycopy(esIdArr, index + 1, newIdsArr, index, esIdArr.length - index - 1);
            System.arraycopy(featureArr, 0, newFeatureArr, 0, index);
            System.arraycopy(featureArr, index + 1, newFeatureArr, index, newFeatureArr.length - index - 1);
            featureSize --;
            esIdArr = newIdsArr;
            featureArr = newFeatureArr;
            log.info("The time use to delete from memory cache by id is " + (System.currentTimeMillis() - start));
            log.info("The size of cache is " + featureSize);
            return true;
        }finally {
            writeLock.unlock();
        }
    }

    /**
     * 增加多条数据
     * @param features
     * @return
     */
    public boolean addFeatures(List<Pair<String, byte[]>> features){
        writeLock.lock();
        try {
            for (Pair<String, byte[]> feature : features) {
                if (featureSize >= featureSizeMax) {
                    expand();
                }
                featureArr[featureSize] = feature.getValue();
                esIdArr[featureSize] = feature.getKey();
                featureSize ++;
            }
            log.info("The size of cache is " + featureSize);
            return true;
        }finally {
            writeLock.unlock();
        }
    }

    public int getFeatureSize(){
        return featureSize;
    }

    public byte[][] getFeatureArr() {
        return featureArr;
    }

    public int getFeatureSizeMax(){
        return featureSizeMax;
    }

    public String getId(int index){
        return esIdArr[index];
    }

    public void reLoadFeatures(byte[][] featureArr, String[] esIdArr){
        writeLock.lock();
        if(featureArr.length != esIdArr.length){
            log.error("The datas reload is irregular");
            return;
        }

        try {
            this.featureArr = featureArr;
            this.esIdArr = esIdArr;
            this.featureSize = featureArr.length;
            log.info("The size of cache is " + featureSize);
        }finally {
            writeLock.unlock();
        }
    }
}
