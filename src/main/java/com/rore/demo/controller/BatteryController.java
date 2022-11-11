package com.rore.demo.controller;

import com.rore.demo.dto.common.BatteryDTO;
import com.rore.demo.dto.request.PostcodeRangeInput;
import com.rore.demo.dto.response.DataResponse;
import com.rore.demo.service.BatteryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/battery")
public class BatteryController {

    @Autowired
    BatteryService service;

    @ResponseBody
    @PostMapping("/postcode-range")
    public DataResponse getBatteryByPostcodeRange(@RequestBody PostcodeRangeInput input){
        return service.getBatteryByPostcodeRange(input);
    }

    @ResponseBody
    @PostMapping("/list")
    public DataResponse list(@RequestBody PostcodeRangeInput input){
        return service.getBatteryByPostcodeRange(input);
    }

    @ResponseBody
    @PostMapping("/create")
    public BatteryDTO create(@RequestBody BatteryDTO input){
        return service.create(input);
    }

    @ResponseBody
    @PostMapping("/update")
    public BatteryDTO update(@RequestBody BatteryDTO input){
        return service.update(input);
    }

    @ResponseBody
    @PostMapping("/delete")
    public BatteryDTO delete(@RequestBody BatteryDTO input){
        return service.deleteById(input);
    }



}