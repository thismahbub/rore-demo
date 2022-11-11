package com.rore.demo.dto.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import lombok.Data;

import java.util.HashMap;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetListByOidSetRequestBodyDTO {

    private Set<String> oids;
    private String strict;

    public String toJson(){
        return new Gson().toJson(this);
    }

    public HashMap<String, Object> toHashMap(){
        return new Gson().fromJson(this.toJson(), HashMap.class);
    }

}