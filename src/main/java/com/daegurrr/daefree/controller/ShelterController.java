package com.daegurrr.daefree.controller;

import com.daegurrr.daefree.dto.shelter.ShelterRequest;
import com.daegurrr.daefree.dto.shelter.ShelterResponse;
import com.daegurrr.daefree.service.ShelterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@Tag(name = "Shelter", description = "쉼터 정보 검색 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shelter")
public class ShelterController {
    private final ShelterService shelterService;

    // 종류로 근처 무더위 쉼터를 탐색
    @PostMapping("/type")
    @Operation(summary = "쉼터 종류로 근처 무더위 쉼터를 탐색")
    public ResponseEntity<List<ShelterResponse.Position>> searchPositions(@RequestBody ShelterRequest.Facility facility) {
        return ResponseEntity.ok().body(shelterService.searchByType(facility.getFacilityType().getDescription()));
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "쉼터 id로 무더위 쉼터의 상세 정보 반환")
    public ResponseEntity<ShelterResponse.Detail> searchDetail(@PathVariable Long id) {
        return ResponseEntity.ok().body(shelterService.searchDetailByPosition(id));
    }
    @PostMapping("/nearby")
    @Operation(summary = "위도, 경도 기반 거리 계산으로 현재 위치 주변의 무더위 쉼터 정보 반환")
    public ResponseEntity<List<ShelterResponse.Detail>> searchNearByDetails(@RequestBody ShelterRequest.Position position) {
        return ResponseEntity.ok().body(shelterService.searchNearbyDetails(position.getLatitude(), position.getLongitude()));
    }

    @Hidden
    @GetMapping("/test4")
    @Operation(summary = "테스트용 API")
    public ResponseEntity<Void> test4() throws URISyntaxException, JsonProcessingException, InterruptedException {
        shelterService.saveTest(30);
        return ResponseEntity.ok().build();
    }
}
