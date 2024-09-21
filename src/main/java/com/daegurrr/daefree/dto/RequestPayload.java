package com.daegurrr.daefree.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestPayload {
    @JsonProperty("searchInfo")
    @Builder.Default private SearchInfo searchInfo = new SearchInfo();

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchInfo {
        @Builder.Default private int pageIndex = 0;
        @Builder.Default private String pageUnit = "10";
        @Builder.Default private String pageSize = "10";
        @Builder.Default private String firstIndex = "1";
        @Builder.Default private String lastIndex = "1";
        @Builder.Default private String recordCountPerPage = "10";
        @Builder.Default private String q_area_cd_3 = "";
        @Builder.Default private String q_area_cd_2 = "";
        @Builder.Default private String q_area_cd_1 = "27";
        @Builder.Default private String searchRstrNm = "";
        @Builder.Default private String searchFcltyTy = "";
        @Builder.Default private String searchYear = "2024";
        @Builder.Default private String searchChckAirconAt = "";
        @Builder.Default private String govAreaCode = "";
        @Builder.Default private String parntsBdongCd = "";
        @Builder.Default private String searchCdKey = "";
        @Builder.Default private String searchGb = "pageSearch";
        @Builder.Default private String acmdfclty_cd = "";
        @Builder.Default private String fcltyGrad = "";
        @Builder.Default private String usePsblDiv = "";
    }
}