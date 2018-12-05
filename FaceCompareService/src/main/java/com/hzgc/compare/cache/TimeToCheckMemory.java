package com.hzgc.compare.cache;

import java.util.TimerTask;

public class TimeToCheckMemory extends TimerTask {

    public void run() {
        FeatureCache.getInstance().checkMemory();
    }
}
