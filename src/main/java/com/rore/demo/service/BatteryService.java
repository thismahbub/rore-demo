package com.rore.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rore.demo.conostant.CommonConstant;
import com.rore.demo.dto.BaseDTO;
import com.rore.demo.dto.common.BatteryDTO;
import com.rore.demo.dto.request.PostcodeRangeInput;
import com.rore.demo.dto.response.BaseResponse;
import com.rore.demo.dto.response.DataResponse;
import com.rore.demo.entity.Battery;
import com.rore.demo.exception.ApiRequestException;
import com.rore.demo.repository.BatteryRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class BatteryService extends BaseService<Battery, BatteryDTO>{

    @Autowired
    BatteryRepository repository;

    public BatteryService(BatteryRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper);
        this.repository = repository;
    }

    public DataResponse getBatteryByPostcodeRange(PostcodeRangeInput input){
        DataResponse response = new DataResponse();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
        baseResponse.setResponseType(CommonConstant.SUCCESS);

        List<Battery> datas = repository.getBatteryByPostCodeRage(input.getPostcodeStart(),input.getPostcodeEnd());

        int[] total = {0};
        if (null != datas && datas.size() > 0){
            ObjectMapper objectMapper = new ObjectMapper();
            List<HashMap<String, Object>> tableData = new ArrayList<>();
            datas.forEach(data->{
                BatteryDTO dto = new BatteryDTO();
                dto.setOid(data.getOid().toString());
                dto.setName(data.getName());
                dto.setPostcode(data.getPostcode());
                dto.setWatt(data.getWatt());

                HashMap<String, Object> map = objectMapper.convertValue(dto, HashMap.class);
                tableData.add(map);

                total[0]=total[0]+dto.getWatt();
            });
            response.setTableData(tableData);
            response.setDataCount(datas.size());
            response.setTotalWatt(total[0]);
            float average = total[0]/datas.size();
            response.setAverageWatt(average);
        }else {
            throw new ApiRequestException(CommonConstant.DATA_NOT_FOUND);
        }

        response.setResponse(baseResponse);
        return response;
    }

    public DataResponse getListData() {
        return new DataResponse();
    }

    @Override
    public BatteryDTO create(BatteryDTO requestDTO) {
        return super.create(requestDTO);
    }

    @Transactional
    public BatteryDTO update(BatteryDTO requestDTO) {
        return super.update(requestDTO);
    }

    @Transactional
    public BatteryDTO deleteById(BaseDTO requestDTO) {
        return super.deleteById(requestDTO);
    }


}