package com.example.models;

import java.io.Serializable;

public class CompositeKey implements Serializable {
    private Integer firstKey;
    private Integer secondKey;

    public CompositeKey(){

    }

    public CompositeKey(Integer first_key, Integer second_key){
        this.firstKey = first_key;
        this.secondKey = second_key;
    }

    public Integer getFirstKey(){
        return firstKey;
    }

    public Integer getSecondKey(){
        return secondKey;
    }

}
