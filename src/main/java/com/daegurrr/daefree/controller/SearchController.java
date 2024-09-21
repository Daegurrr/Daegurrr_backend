package com.daegurrr.daefree.controller;

import com.daegurrr.daefree.dto.HeatWaveShelterResponse;
import com.daegurrr.daefree.dto.SafeKoreaResponse;
import com.daegurrr.daefree.dto.SearchRequest;
import com.daegurrr.daefree.dto.SearchResponse;
import com.daegurrr.daefree.service.SearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {
    private final SearchService searchService;

    // 종류로 근처 무더위 쉼터를 탐색
    @GetMapping("/position")
    public ResponseEntity<List<SearchResponse.Position>> searchPositions(@RequestBody SearchRequest.Facility facility) {
        return ResponseEntity.ok().body(searchService.searchByType(facility.getFacilityType().getDescription()));
    }
    // 위도, 경도로 해당 무더위 쉼터의 상세 정보 반환
    @GetMapping("/detail/{id}")
    public ResponseEntity<SearchResponse.Detail> searchDetail(@PathVariable Long id) {
        return ResponseEntity.ok().body(searchService.searchDetailByPosition(id));
    }
    @GetMapping("/nearby")
    public ResponseEntity<List<SearchResponse.Detail>> searchNearByDetails(@RequestBody SearchRequest.Position position) {
        return ResponseEntity.ok().body(searchService.searchNearbyDetails(position.getLatitude(), position.getLongitude()));
    }

}
