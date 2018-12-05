package com.hzgc.compare.es;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.log4j.Logger;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class ElasticSearchHelper implements Serializable {
    private static Logger LOG = Logger.getLogger(ElasticSearchHelper.class);
    private static TransportClient client = null;

    public ElasticSearchHelper() {
    }

    public static TransportClient getEsClient(String clusterName, String esHost, int esPort) {
        if (null == client) {
            initElasticSearchClient(clusterName, esHost, esPort);
        }

        return client;
    }

    private static void initElasticSearchClient(String clusterName, String esHost, int esPort) {
        LOG.info("===================================================================");
        LOG.info("Start init elasticSearch client, clusterName is:" + clusterName + ", ip address is:" + esHost + ", es port is:" + esPort);
        Settings settings = Settings.builder().put("cluster.name", clusterName).put("client.transport.sniff", false).build();
        client = new PreBuiltTransportClient(settings, new Class[0]);
        String[] var4 = esHost.split(",");
        int var5 = var4.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String host = var4[var6];

            try {
                client.addTransportAddress(new TransportAddress(InetAddress.getByName(host), esPort));
                LOG.info("Address addition successed!");
            } catch (UnknownHostException var9) {
                LOG.error("Host can not be identify!");
                var9.printStackTrace();
            }
        }

        LOG.info("===================================================================");
    }
}
