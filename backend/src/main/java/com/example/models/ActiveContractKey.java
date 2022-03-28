package com.example.models;

import java.io.Serializable;

public class ActiveContractKey implements Serializable {
    private Integer student;
    private Integer specialization;

    public ActiveContractKey(){

    }

    public ActiveContractKey(Integer first_key, Integer second_key){
        this.student = first_key;
        this.specialization = second_key;
    }

    public Integer getStudentID(){
        return student;
    }

    public Integer getSpecializationID(){
        return specialization;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ActiveContractKey))
            return false;
        ActiveContractKey activeContractKey = (ActiveContractKey) o;
        return student.equals((activeContractKey.getStudentID())) &&
                specialization.equals(activeContractKey.getSpecializationID());
    }

    @Override
    public int hashCode() {
        int result = student != null ? student.hashCode() : 0;
        result = 31 * result + (specialization != null ? specialization.hashCode() : 0);
        return result;
    }
}
