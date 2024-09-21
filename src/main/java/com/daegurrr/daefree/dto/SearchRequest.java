package com.daegurrr.daefree.dto;

import com.daegurrr.daefree.enumeration.FacilityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class SearchRequest {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Facility {
        private FacilityType facilityType;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Position {
        private Double latitude;
        private Double longitude;
    }
}
