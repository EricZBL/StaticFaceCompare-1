package com.hzgc.compare.service;


import com.hzgc.common.CompareParam;
import com.hzgc.common.SearchResult;
import com.hzgc.common.Service;
import com.hzgc.common.UpdateParam;
import com.hzgc.common.rpc.client.result.AllReturn;
import com.hzgc.compare.compare.CompareOnePerson;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceImpl implements Service {
    @Override
    public AllReturn<SearchResult> retrievalOnePerson(CompareParam param) {
        CompareOnePerson compareOnePerson = new CompareOnePerson();
        SearchResult searchResult = compareOnePerson.compare(param.getFeatureBit(), param.getFeature(), param.getSim());
        return new AllReturn<>(searchResult);
    }

    @Override
    public AllReturn<Boolean> add(UpdateParam updateParam) {
        return null;
    }

    @Override
    public AllReturn<Boolean> delete(UpdateParam updateParam) {
        return null;
    }

    @Override
    public AllReturn<String> test() throws InterruptedException {
        log.info("TEST ");
        return new AllReturn<>("response");
    }
}
