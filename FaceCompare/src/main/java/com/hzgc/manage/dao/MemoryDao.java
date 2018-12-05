package com.hzgc.manage.dao;

import com.hzgc.manage.vo.SingleSearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Slf4j
public class MemoryDao {
    private Map<String, SingleSearchResult> searchCollectionMap = new ConcurrentHashMap<>();
    private List<String> ids = new CopyOnWriteArrayList<>();
    private ReentrantLock lock = new ReentrantLock();

    public boolean insertSearchRes(SingleSearchResult singleSearchResult) {
        try {
            lock.lock();
            log.info("Insert " + singleSearchResult.getSearchId());
            if(searchCollectionMap.size() > 100){
                removeLastResult();
                searchCollectionMap.put(singleSearchResult.getSearchId(), singleSearchResult);
                ids.add(singleSearchResult.getSearchId());
                return true;
            }
            searchCollectionMap.put(singleSearchResult.getSearchId(), singleSearchResult);
            ids.add(singleSearchResult.getSearchId());
        } finally {
            lock.unlock();
        }
        return true;
    }

    public SingleSearchResult getSearchRes(String searchId) {
        try {
            lock.lock();
            if (searchCollectionMap.containsKey(searchId)) {
                log.info("Get search result successfull, search id is:{}", searchId);
                log.info("total is :"+searchCollectionMap.get(searchId).getTotal()+", Person ::" +searchCollectionMap.get(searchId).getPersonVOS().get(0).toString());
                return searchCollectionMap.get(searchId);
            } else {
                log.warn("Search result is not exists, search id is:{}", searchId);
                return null;
            }
        } finally {
            lock.unlock();
        }
    }

    private void removeLastResult() {
        for (int i = 0; i < 50; i++) {
            String id = ids.get(0);
            searchCollectionMap.remove(id);
            ids.remove(0);
        }

    }
}



