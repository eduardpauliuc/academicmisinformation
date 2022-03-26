package com.example.models;

import java.io.Serializable;

public class CompositeKey implements Serializable {
    private Integer firstKey;
    private Integer secondKey;

    public CompositeKey(){

    }

    public CompositeKey(Integer firstKey, Integer secondKey){
        this.firstKey = firstKey;
        this.secondKey = secondKey;
    }

    public Integer getFirstKey(){
        return this.firstKey;
    }

    public Integer getSecondKey(){
        return this.secondKey;
    }

}
