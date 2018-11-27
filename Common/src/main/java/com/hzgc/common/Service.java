package com.hzgc.common;

import com.hzgc.common.rpc.client.result.AllReturn;

public interface Service {
    AllReturn<SearchResult> retrievalOnePerson(CompareParam param);

    AllReturn<Boolean> add(UpdateParam updateParam);

    AllReturn<Boolean> delete(UpdateParam updateParam);

    AllReturn<String> test() throws InterruptedException;
}
