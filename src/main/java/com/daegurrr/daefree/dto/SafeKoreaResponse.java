package com.daegurrr.daefree.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SafeKoreaResponse {
    @JsonProperty("htwRstrList")
    private List<Facility> facilityList;
    @JsonProperty("rtnResult")
    private Result result;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Facility {
        @JsonProperty("DTL_ADRES")
        private String addressDetail;
        @JsonProperty("LO")
        private double longitude;
        @JsonProperty("WKEND_HDAY_OPER_BEGIN_TIME")
        private String weekendHolidayOperationBeginTime;
        @JsonProperty("RN_DTL_ADRES")
        private String roadNameDetailAddress;
        @JsonProperty("USE_PSBL_DIV")
        private String usePossibleDivision;
        @JsonProperty("ARCD")
        private String areaCode;
        @JsonProperty("WKDAY_OPER_END_TIME")
        private String weekdayOperationEndTime;
        @JsonProperty("WKDAY_OPER_BEGIN_TIME")
        private String weekdayOperationBeginTime;
        @JsonProperty("RNUM")
        private int rowNumber;
        @JsonProperty("USE_PSBL_NMPR")
        private int usePossibleNumber;
        @JsonProperty("FCLTY_TY_CD")
        private String facilityTypeCode;
        @JsonProperty("LA")
        private double latitude;
        @JsonProperty("RSTR_NM")
        private String facilityName;
        @JsonProperty("WKEND_HDAY_OPER_END_TIME")
        private String weekendHolidayOperationEndTime;
        @JsonProperty("FCLTY_TY")
        private String facilityType;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        @JsonProperty("totCnt")
        private int totalCount;
        @JsonProperty("resultCode")
        private String resultCode;
        @JsonProperty("pageSize")
        private int pageSize;
        @JsonProperty("resultMsg")
        private String resultMessage;
    }
}
