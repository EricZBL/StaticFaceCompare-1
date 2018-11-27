package com.hzgc.compare;


import com.hzgc.compare.common.PropertiesUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class Config {
    public static final String SERVICE_PATH = "/static_compare_service";
    public static String ES_CLUSTER_NAME;
    public static String ES_HOST;
    public static int ES_CLUSTER_PORT;
    public static int SERVICE_NUM;
    public static int FILES_PER_THREAD;
    public static int SERVICE_ID;
    public static String FILE_PATH;
    public static String ZK_ADDRESS;
    public static String WORKER_ADDRESS;

    static {
        Properties prop = PropertiesUtil.getProperties();
        ES_CLUSTER_NAME = prop.getProperty("es.cluster.name");
        ES_HOST = prop.getProperty("es.hosts");
        String sysVar = System.getProperty("es.hosts");
        if(sysVar != null && sysVar.length() != 0){
            ES_HOST = sysVar;
        }
        ES_CLUSTER_PORT = Integer.parseInt(prop.getProperty("es.cluster.port"));
        SERVICE_NUM = Integer.parseInt(prop.getProperty("service.num"));
        FILES_PER_THREAD = Integer.parseInt(prop.getProperty("files.per.thread"));
        FILE_PATH = prop.getProperty("file.path");
        ZK_ADDRESS = prop.getProperty("zookeeper.address");
        sysVar = System.getProperty("zk.address");
        if(sysVar != null && sysVar.length() != 0){
            ZK_ADDRESS = sysVar;
        }
        WORKER_ADDRESS = getLocalIpAddress();
    }

    private static String getLocalIpAddress(){
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            return ip;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }
}
