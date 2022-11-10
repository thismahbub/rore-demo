package com.rore.demo.conostant;

public interface CommonConstant {

    String SUCCESS = "Success";
    String ERROR = "Error";

    String SUCCESS_CODE = "000";
    String ERROR_CODE = "100";
    String API_REQUEST_FAIL = "501";
    String HTTP_4XX_ERROR = "Invalid request, Unable to process at this moment. Please try after sometime";
    String HTTP_5XX_ERROR = "Internal processing error, Unable to process at this moment. Please try after sometime.";
    String TIMEOUT_ERROR = "Request processing failed due to system delay. Please try after sometime.";

    String COMMON_ERROR = "Request processing failed. Please try again";

    String DATA_NOT_FOUND = "Data not found.";
    String NO_DUE = "No due found.";

    String DEFAULT_PAGE_NUMBER = "0";
    String DEFAULT_PAGE_SIZE = "10";
    String DEFAULT_SORT_BY = "id";
    String DEFAULT_SORT_DIRECTION = "asc";

}