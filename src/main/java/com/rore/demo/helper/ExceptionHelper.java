package com.rore.demo.helper;

import com.rore.demo.conostant.CommonConstant;
import com.rore.demo.dto.response.BaseResponse;
import com.rore.demo.exception.ApiRequestException;
import com.rore.demo.exception.CustomExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHelper {

    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<CustomExceptionResponse> apiExceptionHandler(ApiRequestException e){
        CustomExceptionResponse customException = new CustomExceptionResponse(CommonConstant.ERROR_CODE, e.getMessage());
        return new ResponseEntity<>(customException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> exceptionHandler(Exception e) {
        BaseResponse response = new BaseResponse();
        response.setResponseCode(CommonConstant.ERROR_CODE);
        response.setResponseType(CommonConstant.ERROR);
        response.setResponseMessage(CommonConstant.HTTP_5XX_ERROR);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}