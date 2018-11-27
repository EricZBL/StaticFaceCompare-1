package com.hzgc.jniface;

import java.util.ArrayList;

public class FaceCompareUtil {
    private static byte[] hamming = new byte[]{
            0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4, 1, 2, 2, 3, 2, 3,
            3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4,
            3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 1, 2,
            2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5,
            3, 4, 4, 5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5,
            5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 1, 2, 2, 3,
            2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4,
            4, 5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6,
            3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 2, 3, 3, 4, 3, 4,
            4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6,
            5, 6, 6, 7, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 4, 5,
            5, 6, 5, 6, 6, 7, 5, 6, 6, 7, 6, 7, 7, 8
    };

    private int HANMING_THRESHOLD = 200;
    private int SAVE_INDEX_MAX = 999;
    private long[][] arr = new long[257 - HANMING_THRESHOLD][SAVE_INDEX_MAX + 1];
    private int[] indexes = new int[257 - HANMING_THRESHOLD];

    public FaceCompareUtil(int hanming_Threshold, int saveIndexMax){
        HANMING_THRESHOLD = hanming_Threshold;
        SAVE_INDEX_MAX = saveIndexMax;
        arr = new long[257 - HANMING_THRESHOLD][SAVE_INDEX_MAX + 1];
        indexes = new int[257 - HANMING_THRESHOLD];
    }

    public long[][] faceCompareBitOne(String index, byte[][] featureList, byte[] queryList, int topN, long[][] resBuffer){
        if(resBuffer.length == HANMING_THRESHOLD){
            arr = resBuffer;
            for(long j = 0; j < featureList.length; j++){
                int dist = 0;
                for(int i = 0; i < 32 ; i ++){
                    dist += hamming[~ (queryList[i] ^ featureList[(int) j][i]) & 0xFF];
                }
                if (dist > HANMING_THRESHOLD) {
                    int n = dist - HANMING_THRESHOLD;
                    if(indexes[n] <= SAVE_INDEX_MAX){
                        arr[n][indexes[n]] = dist + (j << 9);
                        indexes[n] ++;
                    }
                }
            }
        }

        return arr;
    }

    public CompareResult faceCompareBitOne(String index, byte[][] featureList, byte[] queryList, int topN){
        for(long j = 0; j < featureList.length; j++){
            int dist = 0;
            for(int i = 0; i < 32 ; i ++){
                dist += hamming[~ (queryList[i] ^ featureList[(int) j][i]) & 0xFF];
            }
            if (dist > HANMING_THRESHOLD) {
                int n = dist - HANMING_THRESHOLD;
                if(indexes[n] <= SAVE_INDEX_MAX){
                    arr[n][indexes[n]] = dist + (j << 9);
                    indexes[n] ++;
                }
            }
        }

        CompareResult compareResult = new CompareResult();
        ArrayList<FaceFeatureInfo> faceFeatureInfos = new ArrayList<>();
        int resSize = 0;
        int arrIndex = arr.length -1;
        while(resSize < topN){
            long[] ress = arr[arrIndex];
            int resIndexMax = indexes[arrIndex] - 1;
            int resIndex = 0;
            while(resIndex <= resIndexMax && resSize < topN){
                long res = ress[resIndex];
                long dist = res % 512;
                long j = res / 512;
                FaceFeatureInfo faceFeatureInfo = new FaceFeatureInfo();
                faceFeatureInfo.setDist((int) dist);
                faceFeatureInfo.setIndex((int) j);
                faceFeatureInfos.add(faceFeatureInfo);
                resIndex ++;
                resSize ++;
            }
            arrIndex --;
        }
        compareResult.setPictureInfoArrayList(faceFeatureInfos);
        compareResult.setIndex(index);

        return compareResult;

    }
}
