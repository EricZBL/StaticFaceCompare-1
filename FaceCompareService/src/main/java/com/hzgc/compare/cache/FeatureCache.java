package com.hzgc.compare.cache;

import com.hzgc.compare.common.Pair;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class FeatureCache {
    private static FeatureCache featureCache;
    private ReentrantLock writeLock = new ReentrantLock();
    private byte[][] featureArr;
    private String[] esIdArr;
    private static int FEA_SIZE_DEFAULT = 10000000;
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
        featureSizeMax += 5000000;
        byte[][] newFeatureArr = new byte[featureSizeMax][32];
        System.arraycopy(featureArr, 0, newFeatureArr, 0, newFeatureArr.length);
        String[] newEsIdArr = new String[featureSizeMax];
        System.arraycopy(esIdArr, 0, newEsIdArr, 0, newEsIdArr.length);
    }

    public boolean addFeature(String id, byte[] feature){
        writeLock.lock();
        try {
            if(featureSize >= featureSizeMax){
                expand();
            }
            featureArr[featureSize] = feature;
            esIdArr[featureSize] = id;
            featureSize ++;
            return true;
        }finally {
            writeLock.unlock();
        }

    }

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
            return true;
        }finally {
            writeLock.unlock();
        }
    }

    public byte[][] getFeatureArr() {
        return featureArr;
    }

    public String getId(int index){
        return esIdArr[index];
    }
}
