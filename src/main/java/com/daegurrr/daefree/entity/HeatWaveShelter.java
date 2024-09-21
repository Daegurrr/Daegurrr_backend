package com.daegurrr.daefree.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "HEAT_WAVE_SHELTER")
public class HeatWaveShelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String restName;
    private String doroAddress;
    private String jibunAddress;
    private String deptContactNumber;
    private String deptName;
    private int capacity;
    private String operationBeginDate; // api
    private String operationEndDate; // api
    private String openTime;
    private String closeTime;
    private String restType;
    private Double latitude;
    private Double longitude;
    private boolean isNightOperation; // api
    private boolean isWeekendOperation; // api
    private boolean isStayingPossible; // api
    private boolean isPublic;
}
