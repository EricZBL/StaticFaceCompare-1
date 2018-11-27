//package com.hzgc.manage.service.impl;
//
//import com.hzgc.common.CompareParam;
//import com.hzgc.common.SearchResult;
//import com.hzgc.common.Service;
//import com.hzgc.common.UpdateParam;
//import com.hzgc.common.rpc.client.RpcClient;
//import com.hzgc.common.rpc.client.result.AllReturn;
//import com.hzgc.common.rpc.util.Constant;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
////import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@org.springframework.stereotype.Service
//@Slf4j
//public class CompareClient {
//    private Service service;
//
//    public CompareClient(@Value("${zk.address}")String zkAddress){
//        log.info("Create rpc client , zkAddress is : " + zkAddress);
//        createService(zkAddress);
//    }
//
//    private boolean createService(String serverAddress){
//        Constant constant = new Constant("/static_worker_job", "worker");
//        RpcClient rpcClient = new RpcClient(serverAddress, constant);
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        service = rpcClient.createAll(Service.class);
//        return check();
//    }
//
//    public boolean check(){
//        try {
//            AllReturn<String> res = service.test();
//            if(res == null){
//                log.error("Connect to face compare service faild.");
//                return false;
//            }
//            List<String> responses = res.getResult();
//            if(responses == null || responses.size() == 0 || !responses.get(0).equals("response")){
//                log.error("Connect to face compare service faild. Please to confirm the server were started .");
//                return false;
//            }
//        } catch (InterruptedException e) {
//            log.error("Connect to face compare service faild." + e.getMessage());
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//
//
//    public SearchResult retrieval(CompareParam param){
//        AllReturn<SearchResult> searchResults = service.retrievalOnePerson(param);
//        List<SearchResult> searchResultList = searchResults.getResult();
//        if(searchResultList == null || searchResultList.size() == 0){
//            log.error("No result get.");
//        }
//        SearchResult result = searchResultList.get(0);
//        for(int i = 1 ; i < searchResultList.size(); i ++){
//            result.merge(searchResultList.get(i));
//        }
//        return result;
//    }
//
//    public void addData(String esId, String bitFeature, String idCard){
//        AllReturn<Boolean> res = service.add(new UpdateParam(esId, bitFeature, idCard));
////        for(Boolean res : res.getResult())
//    }
//
//    public void delete(String esId, String idCard){
//        service.delete(new UpdateParam(esId, null, idCard));
//    }
//
//}
