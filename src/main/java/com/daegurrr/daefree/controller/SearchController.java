package com.daegurrr.daefree.controller;

import com.daegurrr.daefree.dto.HeatWaveShelterResponse;
import com.daegurrr.daefree.dto.SafeKoreaResponse;
import com.daegurrr.daefree.dto.SearchRequest;
import com.daegurrr.daefree.dto.SearchResponse;
import com.daegurrr.daefree.service.SearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@Tag(name = "Search", description = "쉼터 정보 검색 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {
    private final SearchService searchService;

    // 종류로 근처 무더위 쉼터를 탐색
    @PostMapping("/position")
    @Operation(summary = "쉼터 종류로 근처 무더위 쉼터를 탐색")
    public ResponseEntity<List<SearchResponse.Position>> searchPositions(@RequestBody SearchRequest.Facility facility) {
        return ResponseEntity.ok().body(searchService.searchByType(facility.getFacilityType().getDescription()));
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "쉼터 id로 무더위 쉼터의 상세 정보 반환")
    public ResponseEntity<SearchResponse.Detail> searchDetail(@PathVariable Long id) {
        return ResponseEntity.ok().body(searchService.searchDetailByPosition(id));
    }
    @PostMapping("/nearby")
    @Operation(summary = "위도, 경도 기반 거리 계산으로 현재 위치 주변의 무더위 쉼터 정보 반환")
    public ResponseEntity<List<SearchResponse.Detail>> searchNearByDetails(@RequestBody SearchRequest.Position position) {
        return ResponseEntity.ok().body(searchService.searchNearbyDetails(position.getLatitude(), position.getLongitude()));
    }

    @GetMapping("/test4")
    @Operation(summary = "테스트용 API")
    public ResponseEntity<Void> test4() throws URISyntaxException, JsonProcessingException, InterruptedException {
        searchService.saveTest(30);
        return ResponseEntity.ok().build();
    }
}
