package com.hzgc.compare.es;

import com.hzgc.common.Person;
import com.hzgc.compare.Config;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class ElasticSearchClient {
    private static TransportClient esClient;

    public static void connect(){
        esClient = ElasticSearchHelper.getEsClient(Config.ES_CLUSTER_NAME, Config.ES_HOST, Config.ES_CLUSTER_PORT);
    }

    public static List<Person> readFromEs(List<String> ids){
        SearchRequestBuilder requestBuilder = esClient.prepareSearch(Config.ES_INDEX)
                .setTypes(Config.ES_TYPE);
        requestBuilder.setSize(Config.RES_FIRST_COMPARE);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        TermsQueryBuilder queryBuilder = QueryBuilders.termsQuery("_id", ids.toArray(new String[ids.size()]));
        boolQueryBuilder.must(queryBuilder);
        requestBuilder.setQuery(boolQueryBuilder);
        SearchResponse response = requestBuilder.execute().actionGet();
        SearchHit[] hits = response.getHits().getHits();
        return analyseResult(hits);
    }

    private static List<Person> analyseResult(SearchHit[] hits){
        List<Person> res = new ArrayList<>();
        for(SearchHit hit : hits){
            Person person = new Person();
            Map<String, Object> hitMap = hit.getSourceAsMap();
            person.setId(hit.getId());
            if(hitMap.get("sfz") != null) {
                person.setSfz((String) hitMap.get("sfz"));
            }
            if(hitMap.get("xm") != null){
                person.setXm((String) hitMap.get("xm"));
            }
            if(hitMap.get("xb") != null){
                person.setXb((String) hitMap.get("xb"));
            }
            if(hitMap.get("mz") != null) {
                person.setMz((String) hitMap.get("mz"));
            }
            if(hitMap.get("sr") != null){
                person.setSr((String) hitMap.get("sr"));
            }
            if(hitMap.get("ssssqx") != null) {
                person.setSsssqx((String) hitMap.get("ssssqx"));
            }
            if(hitMap.get("jd") != null){
                person.setJd((String) hitMap.get("jd"));
            }
            if(hitMap.get("mp") != null) {
                person.setMp((String) hitMap.get("mp"));
            }
            if(hitMap.get("mlxz") != null) {
                person.setMlxz((String) hitMap.get("mlxz"));
            }
            if(hitMap.get("csd") != null) {
                person.setCsd((String) hitMap.get("csd"));
            }
            if(hitMap.get("cym") != null) {
                person.setCym((String) hitMap.get("cym"));
            }
            if(hitMap.get("jg") != null) {
                person.setJg((String) hitMap.get("jg"));
            }
            if(hitMap.get("tp") != null) {
                person.setTp((String) hitMap.get("tp"));
            }
            if(hitMap.get("tzz") != null) {
                person.setTzz((String) hitMap.get("tzz"));
            }
            if(hitMap.get("bittzz") != null) {
                person.setBittzz((String) hitMap.get("bittzz"));
            }
            res.add(person);
        }
        log.info("The size of response is " + res.size());
        return res;
    }

    public static void main(String[] args){
        ElasticSearchClient.connect();
        List<String> ids = new ArrayList<>();
        ids.add("0481abc3-abd3-4730-92dd-60188f300126");
        ids.add("24b0ea89-70cb-4c01-a997-fc2ca6ab633d");
        ElasticSearchClient.readFromEs(ids);
    }
}
