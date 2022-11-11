package com.rore.demo.dto.request;

import com.google.gson.Gson;
import lombok.Data;

import java.util.HashMap;

@Data
public class PostcodeRangeInput {

    private Integer postcodeStart;
    private Integer postcodeEnd;

    public String toJson(){
        return new Gson().toJson(this);
    }

    public HashMap<String, Object> toHashMap(){
        return new Gson().fromJson(this.toJson(), HashMap.class);
    }

}