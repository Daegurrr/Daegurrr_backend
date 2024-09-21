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
public class HeatWaveShelterResponse {

    @JsonProperty("HeatWaveShelter")
    private List<HeatWaveShelterData> heatWaveShelter;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HeatWaveShelterData {
        private List<Head> head;
        private List<ShelterInfo> row;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Head {
        private int totalCount;
        private int numOfRows;
        private int pageNo;
        private String type;

        @JsonProperty("RESULT")
        private Result result;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        private String resultCode;
        private String resultMsg;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShelterInfo {
        private String restSeqNo;
        private String year;
        private String areaCd;
        private String equptype;
        private String restname;
        private String restaddr;
        private String creDttm;
        private String updtDttm;
        private String useYn;
        private String areaNm;
        private String operBeginDe;
        private String operEndDe;
        private int ar;
        private int usePsblNmpr;
        private int colrHoldElefn;
        private int colrHoldArcndtn;
        private String chckMatterNightOpnAt;
        private String chckMatterWkendHdayOpnAt;
        private String chckMatterStayngPsblAt;
        private String rm;
        private String dtlAdres;
        private String mngdpt_cd;
        private String mngdptCd;
        private double xcord;
        private double ycord;
        private String fclty_ty_nm;
        private double la;
        private double lo;
        private String mngdpt_nm;
    }
}
