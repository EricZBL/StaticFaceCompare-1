package com.hzgc.compare;

import com.hzgc.compare.cache.FeatureCache;
import com.hzgc.compare.cache.TimeToCheckMemory;
import com.hzgc.compare.es.ElasticSearchClient;
import com.hzgc.jniface.FaceUtil;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;
import java.util.Timer;

@Slf4j
public class ServiceMain {
    public static void main(String args[]){
        //接收参数
        if(args == null || args.length != 1){
            log.error("Args err : " + args);
            System.exit(1);
        }

        try {
            int serviceId = Integer.parseInt(args[0]);
            Config.SERVICE_ID = serviceId;
        } catch (Exception e){
            log.error("Get service Id faild : " + e.getMessage());
            System.exit(1);
        }
        log.info("Get port can bee used");
        String port = getPort();

        log.info("Init caches");
        FeatureCache.getInstance();

        log.info("Connect to ES");
        ElasticSearchClient.connect();

        log.info("Load mate data");
        DealWithDelete dealWithDelete = new DealWithDelete();
        dealWithDelete.loadData();

        log.info("Registry service");
        RPCRegistry rpcRegistry = new RPCRegistry(Config.SERVICE_ID, port);
        Thread thread = new Thread(rpcRegistry);
        thread.start();

        int count = 0;
        while(!rpcRegistry.checkJob()){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
            count ++;
            if(count > 12){
                log.error("Registry to Zookeeper faild.");
                System.exit(1);
            }
        }
//        new Timer().schedule(new TimeToDelete(), 0, 24 * 60 * 60 * 1000);
        FaceUtil.init(Config.HANGMING_SHOULD, Config.RES_FIRST_COMPARE - 1);
//        new Timer().schedule(new TimeToCheckMemory(), 0, 30000);
        log.info("Service start success.");
    }

    private static String getPort(){
        int start = 13000;
        int end = 13500;
        Random ran = new Random();
        boolean flag = true;
        int port = start;
        while(flag){
            port = ran.nextInt(end - start) + start;
            try {
                InetAddress address = InetAddress.getByName("localhost");
                new Socket(address, port);
                flag = true;
            } catch (IOException e) {
                flag = false;
            }
        }
        return port + "";
    }
}
