package com.gino.algorithm;

public class ImplementStr {
    public static int strStr(String haystack, String needle){
        if(needle.length()==0){
            return 0;
        }
        if(!haystack.contains(needle)){
            return -1;
        }
        return haystack.indexOf(needle);
    }
}
