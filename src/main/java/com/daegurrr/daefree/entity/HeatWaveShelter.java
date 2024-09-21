package com.daegurrr.daefree.entity;

import com.daegurrr.daefree.dto.shelter.HeatWaveShelterResponse;
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

    public void updateAdditionalInfo(HeatWaveShelterResponse.ShelterInfo shelterInfo) {
        this.deptName = shelterInfo.getMngdpt_nm();
        this.deptContactNumber = shelterInfo.getMngdptCd();
        this.operationBeginDate = shelterInfo.getOperBeginDe();
        this.operationEndDate = shelterInfo.getOperEndDe();
        this.isNightOperation = shelterInfo.getChckMatterNightOpnAt().equals("Y");
        this.isWeekendOperation = shelterInfo.getChckMatterWkendHdayOpnAt().equals("Y");
        this.isStayingPossible = shelterInfo.getChckMatterStayngPsblAt().equals("Y");
    }
}
