package com.daegurrr.daefree.service;

import com.daegurrr.daefree.dto.HeatWaveShelterResponse;
import com.daegurrr.daefree.dto.RequestPayload;
import com.daegurrr.daefree.dto.SafeKoreaResponse;
import com.daegurrr.daefree.dto.SearchResponse;
import com.daegurrr.daefree.entity.HeatWaveShelter;
import com.daegurrr.daefree.repository.HeatWaveShelterRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final HeatWaveShelterRepository heatWaveShelterRepository;

    @Value("${openapi.secret}")
    private String serviceKey;

    @Override
    public List<SearchResponse.Position> searchByType(String restType) {
        return heatWaveShelterRepository.findByRestType(restType);
    }

    @Override
    public SearchResponse.Detail searchDetailByPosition(Long id) {
        return heatWaveShelterRepository.findDetailById(id);
    }

    @Override
    public List<SearchResponse.Detail> searchNearbyDetails(Double latitude, Double longitude) {
        return heatWaveShelterRepository.findSheltersWithinRadius(latitude, longitude, 500);
    }

    public HeatWaveShelterResponse search() throws URISyntaxException, JsonProcessingException {
        System.out.println(serviceKey);
        WebClient webClient = WebClient.create();
        String url = "https://apis.data.go.kr/1741000/HeatWaveShelter3/getHeatWaveShelterList3?serviceKey=" +
                serviceKey +
                "&pageNo=1&numOfRows=10&type=json&year=2024&areaCd=2711054500&equptype=";
        String result = webClient.get()
                .uri(new URI(url))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(result);
        ObjectMapper objectMapper = new ObjectMapper();
        HeatWaveShelterResponse response = objectMapper.readValue(result, HeatWaveShelterResponse.class);
        return response;
    }

    @Override
    public SafeKoreaResponse search2() {
        String url = "https://www.safekorea.go.kr/idsiSFK/sfk/cs/sfc/selectHtwRstrList.do";
        RequestPayload requestPayload = RequestPayload.builder()
                .searchInfo(RequestPayload.SearchInfo.builder()
                        .pageIndex(1)
                        .pageUnit("30")
                        .pageSize("10")
                        .firstIndex("1")
                        .lastIndex("1")
                        .recordCountPerPage("10")
                        .q_area_cd_1("27")
                        .searchYear("2024")
                        .govAreaCode("27")
                        .searchGb("pageSearch")
                        .build())
                .build();
        WebClient webClient = WebClient.create(url);
        SafeKoreaResponse result = webClient.post()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .bodyValue(requestPayload)
                .retrieve()
                .bodyToMono(SafeKoreaResponse.class)
                .block();
        return result;
    }

    @Override
    public SafeKoreaResponse.Result search3() {
        String url = "https://www.safekorea.go.kr/idsiSFK/sfk/cs/sfc/selectHtwRstrList.do";
        RequestPayload requestPayload = RequestPayload.builder()
                .searchInfo(RequestPayload.SearchInfo.builder()
                        .pageIndex(1)
                        .pageUnit("1")
                        .pageSize("1")
                        .firstIndex("1")
                        .lastIndex("1")
                        .recordCountPerPage("1")
                        .q_area_cd_1("27")
                        .searchYear("2024")
                        .govAreaCode("27")
                        .searchGb("pageSearch")
                        .build())
                .build();
        WebClient webClient = WebClient.create(url);
        SafeKoreaResponse.Result result = webClient.post()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .bodyValue(requestPayload)
                .retrieve()
                .bodyToMono(SafeKoreaResponse.class)
                .block().getResult();
        return result;
    }

    @Override
    public void saveTest() throws URISyntaxException, JsonProcessingException {
        List<HeatWaveShelter> heatWaveShelters = new ArrayList<>();
        HeatWaveShelterResponse heatWaveShelterResponse = search();
        SafeKoreaResponse safeKoreaResponse = search2();
        int pageSize = 30;
        for (int i = 0; i < pageSize; i++) {
//            HeatWaveShelterResponse heatWaveShelterResponse = search();
            SafeKoreaResponse.Facility facility = safeKoreaResponse.getFacilityList().get(i);
            heatWaveShelters.add(HeatWaveShelter.builder()
                    .restName(facility.getFacilityName())
                    .doroAddress(facility.getRoadNameDetailAddress())
                    .jibunAddress(facility.getAddressDetail())
                    .deptName("x")
                    .deptContactNumber("x")
                    .capacity(facility.getUsePossibleNumber())
                    .operationBeginDate("x")
                    .operationEndDate("x")
                    .openTime(facility.getWeekdayOperationBeginTime())
                    .closeTime(facility.getWeekdayOperationEndTime())
                    .restType(facility.getFacilityType())
                    .latitude(facility.getLatitude())
                    .longitude(facility.getLongitude())
                    .isNightOperation(false)
                    .isWeekendOperation(false)
                    .isStayingPossible(false)
                    .isPublic(facility.getUsePossibleDivision().equals("1"))
                    .build());
        }
        for (HeatWaveShelterResponse.ShelterInfo shelterInfo : heatWaveShelterResponse.getHeatWaveShelter().get(1).getRow()) {
            for (HeatWaveShelter heatWaveShelter : heatWaveShelters) {
                if (heatWaveShelter.getLatitude() == shelterInfo.getLa()
                && heatWaveShelter.getLongitude() == shelterInfo.getLo()) {
                    heatWaveShelter.updateAdditionalInfo(shelterInfo);
                    break;
                }
            }
        }
        heatWaveShelterRepository.saveAll(heatWaveShelters);
    }
}
