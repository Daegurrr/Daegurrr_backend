package com.daegurrr.daefree.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class SearchResponse {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Position {
        private Long id;
        private Double latitude;
        private Double longitude;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Detail {
        private Long id;
        private String restName;
        private String doroAddress;
        private String jibunAddress;
        private String deptContactNumber;
        private int capacity;
        private String operationBeginDate;
        private String operationEndDate;
        private String openTime;
        private String closeTime;
        private String restType;
        private Double latitude;
        private Double longitude;
        private boolean isPublic;
    }
}
