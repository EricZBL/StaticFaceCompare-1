package com.hzgc.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchResult {
    private static Integer size = 1000;
    private Record[] records;

    public SearchResult(){
        records = new Record[0];
    }

    public SearchResult(Record[] records){
        this.records = records;
    }

    /**
     * 获取数据的前num条，封装成新的SearchResult
     * @param num
     * @return
     */
    public SearchResult take(int num){
        if(num > records.length){
            return this;
        }
        Record[] recordsTemp = new Record[num];
        System.arraycopy(records, 0, recordsTemp, 0, num);
        return new SearchResult(recordsTemp);
    }

    public SearchResult take(int start, int num){
        if(start >= records.length){
            return new SearchResult();
        }
        Record[] recordsTemp = new Record[num];
        System.arraycopy(records, start, recordsTemp, 0, num);
        return new SearchResult(recordsTemp);
    }

    /**
     * 将当前的records根据Sim排序
     */
    public void sortBySim(){ //TODO 选择合适的排序
//        Arrays.sort(records);
        Arrays.sort(records, (o1, o2) -> Float.compare(o2.sim, o1.sim));
//        quickSort(records, 0, records.length - 1);
    }


    public void filterBySim(float simple){
        List<Record> recordList = new ArrayList<>();
        for(Record record : records){
            if(record.sim > simple){
                recordList.add(record);
            }
        }
        records = recordList.toArray(new Record[recordList.size()]);
    }

//    public static void main(String args[]){
//        List<Integer> sorts = new ArrayList<>();
//        sorts.add(1);
//        sorts.add(2);
//        List<SortParam> sortParams = sorts.stream().map(param -> SortParam.values()[param]).collect(Collectors.toList());
//        System.out.println(sortParams);
//    }

    /**
     * 将多个SearchResult的 records 合并，并根据Sim排序
     * @param result
     * @return
     */
    public void merge(SearchResult result){
        if(result == null || result.getRecords().length == 0){
            return;
        }
        if(records == null|| records.length == 0) {
            records = result.getRecords();
        } else {
            Record[] arr1 = records;
            Record[] arr2 = result.getRecords();
            Record[] arr3 =  new Record[arr2.length + arr1.length];
            int i , j , k;
            i = j = k = 0;
            while (i < arr1.length && j < arr2.length){
                if(arr1[i].compareTo(arr2[j]) > 0){
                    arr3[k++] = arr1[i++];
                } else {
                    arr3[k++] = arr2[j++];
                }
            }
            while (i < arr1.length){
                arr3[k++] = arr1[i++];
            }
            while (j < arr2.length){
                arr3[k++] = arr2[j++];
            }
            records = arr3;
        }
    }



    public Record[] getRecords(){
        return records;
    }


    public static class Record implements  Comparable<Record>{
        float sim;
        Object body;
        public Record(float sim, Object body){
            this.sim = sim;
            this.body = body;
        }

        public float getKey(){
            return sim;
        }

        public Object getValue(){
            return body;
        }

        public int compareTo(Record o) {
            return Float.compare(this.sim, o.sim);
        }

        @Override
        public String toString() {
            return "Record{" +
                    "sim=" + sim +
                    ", body=" + body +
                    '}';
        }
    }


}
