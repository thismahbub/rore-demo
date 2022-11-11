package com.rore.demo.dto.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import com.rore.demo.dto.BaseDTO;
import lombok.Data;
import lombok.ToString;

import java.util.HashMap;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BatteryDTO implements BaseDTO {

    private String oid;
    private String name;
    private Integer postcode;
    private String watt;

    public String toJson(){
        return new Gson().toJson(this);
    }

    public HashMap<String, Object> toHashMap(){
        return new Gson().fromJson(this.toJson(), HashMap.class);
    }

}
