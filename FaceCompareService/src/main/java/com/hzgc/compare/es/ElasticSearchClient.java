package com.hzgc.compare.es;

import com.hzgc.common.Person;
import com.hzgc.common.util.es.ElasticSearchHelper;
import com.hzgc.compare.Config;
import org.elasticsearch.client.transport.TransportClient;

import java.util.List;

public class ElasticSearchClient {
    private static TransportClient esClient;

    public static void connect(){
        esClient = ElasticSearchHelper.getEsClient(Config.ES_CLUSTER_NAME, Config.ES_HOST, Config.ES_CLUSTER_PORT);
    }

    public static List<Person> readFromEs(List<String> ids){
        return null;
    }
}
