package com.hzgc.manage.service.impl;

import com.hzgc.client.CompareClient;
import com.hzgc.common.CompareParam;
import com.hzgc.common.SearchResult;
import com.hzgc.jniface.FaceUtil;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Client {
    private CompareClient client;
    private String zkAddress;

    public Client(@Value("${zk.address}")String zkAddress){
        log.info("Create rpc client , zkAddress is : " + zkAddress);
        this.zkAddress = zkAddress;
        client = new CompareClient();
        client.createService(zkAddress);
    }

    private boolean reConnect(){
        log.info("Reconnect to service.");
        client = new CompareClient();
        return client.createService(zkAddress);
    }

    private void connectionCheck(){
        boolean res = client.check();
        for(int i = 0; i < 3; i ++){
            res = reConnect();
            if(res){
                break;
            }
        }
    }

    public SearchResult compare(CompareParam param){
        return client.retrieval(param);
    }

    public void addData(String esId, String bitFeature, String idCard){
        client.addData(esId, bitFeature, idCard);
    }

    public void delete(String esId, String idCard){
        client.delete(esId, idCard);
    }

    public static void main(String args[]){
        Client client = new Client("172.18.18.103:2181");
        CompareParam param = new CompareParam();
        param.setFeature(FaceUtil.base64Str2floatFeature("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"));
        param.setFeatureBit(FaceUtil.base64Str2BitFeature("n3kXnhr5xvXNugR++Mw8tf2SApi1iF3giUIaM7YkxrA="));
        param.setSim(70);
        SearchResult searchResult = client.compare(param);
        System.out.println(searchResult.getRecords().length);

    }
}
