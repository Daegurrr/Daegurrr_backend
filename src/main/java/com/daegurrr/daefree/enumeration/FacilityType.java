package com.daegurrr.daefree.enumeration;

public enum FacilityType {
    SENIOR_CENTER("001", "노인시설"),
    WELFARE_CENTER("002", "복지회관"),
    VILLAGE_HALL("003", "마을회관"),
    HEALTH_CENTER("004", "보건소"),
    COMMUNITY_CENTER("005", "주민센터"),
    LOCAL_OFFICE("006", "면동사무소"),
    RELIGIOUS_FACILITY("007", "종교시설"),
    FINANCIAL_INSTITUTION("008", "금융기관"),
    PAVILION("009", "정자"),
    PARK("010", "공원"),
    PAVILION_PAGODA("011", "정자, 파고라"),
    BRIDGE_UNDERPASS("013", "교량하부"),
    TREE_SHADE("014", "나무그늘"),
    RIVERSIDE("015", "하천둔치"),
    OTHER("099", "기타");

    private final String code;
    private final String description;

    FacilityType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }

    public static FacilityType fromCode(String code) {
        for (FacilityType type : FacilityType.values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}