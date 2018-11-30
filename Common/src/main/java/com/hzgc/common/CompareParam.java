package com.hzgc.common;

public class CompareParam {
    //比特位特征值
    private byte[] featureBit;
    //float特征值
    private float[] feature;
    //相似度阈值
    private float sim;

    public CompareParam(){

    }

    public CompareParam(byte[] featureBit, float[] feature, float sim) {
        this.featureBit = featureBit;
        this.feature = feature;
        this.sim = sim;
    }

    public byte[] getFeatureBit() {
        return featureBit;
    }

    public void setFeatureBit(byte[] featureBit) {
        this.featureBit = featureBit;
    }

    public float[] getFeature() {
        return feature;
    }

    public void setFeature(float[] feature) {
        this.feature = feature;
    }

    public float getSim() {
        return sim;
    }

    public void setSim(float sim) {
        this.sim = sim;
    }
}
