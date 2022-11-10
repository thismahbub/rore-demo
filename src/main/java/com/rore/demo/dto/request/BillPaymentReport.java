package com.rore.demo.dto.request;

import com.google.gson.Gson;
import lombok.Data;

import java.util.HashMap;

@Data
public class BillPaymentReport {

    private String logo;

    public String toJson(){
        return new Gson().toJson(this);
    }

    public HashMap<String, Object> toHashMap(){
        return new Gson().fromJson(this.toJson(), HashMap.class);
    }

}