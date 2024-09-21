package com.daegurrr.daefree.service;

import com.daegurrr.daefree.dto.HeatWaveShelterResponse;
import com.daegurrr.daefree.dto.RequestPayload;
import com.daegurrr.daefree.dto.SafeKoreaResponse;
import com.daegurrr.daefree.dto.SearchResponse;
import com.daegurrr.daefree.entity.AreaCode;
import com.daegurrr.daefree.entity.HeatWaveShelter;
import com.daegurrr.daefree.repository.AreaCodeRepository;
import com.daegurrr.daefree.repository.HeatWaveShelterRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final HeatWaveShelterRepository heatWaveShelterRepository;
    private final AreaCodeRepository areaCodeRepository;
    private final ObjectMapper objectMapper;

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


//    @Override
//    public SafeKoreaResponse.Result search3() {
//        String url = "https://www.safekorea.go.kr/idsiSFK/sfk/cs/sfc/selectHtwRstrList.do";
//        RequestPayload requestPayload = RequestPayload.builder()
//                .searchInfo(RequestPayload.SearchInfo.builder()
//                        .pageIndex(1)
//                        .pageUnit("1")
//                        .pageSize("1")
//                        .firstIndex("1")
//                        .lastIndex("1")
//                        .recordCountPerPage("1")
//                        .q_area_cd_1("27")
//                        .searchYear("2024")
//                        .govAreaCode("27")
//                        .searchGb("pageSearch")
//                        .build())
//                .build();
//        WebClient webClient = WebClient.create(url);
//        SafeKoreaResponse.Result result = webClient.post()
//                .header("Content-Type", "application/json")
//                .header("Accept", "application/json")
//                .bodyValue(requestPayload)
//                .retrieve()
//                .bodyToMono(SafeKoreaResponse.class)
//                .block().getResult();
//        return result;
//    }

    @Override
    public void saveTest(int pageSize) throws URISyntaxException, JsonProcessingException, InterruptedException {
        List<HeatWaveShelter> heatWaveShelters = new ArrayList<>();
        List<String> areaCodes = areaCodeRepository.findAll().stream().map(AreaCode::getAreaCode).toList();
        SafeKoreaResponse safeKoreaResponse = getShelterInfo(1, pageSize);
        int totalPages = safeKoreaResponse.getResult().getPageSize();
        for (int i = 0; i < totalPages; i++) {
            for (SafeKoreaResponse.Facility facility : safeKoreaResponse.getFacilityList()) {
                heatWaveShelters.add(HeatWaveShelter.builder()
                        .restName(facility.getFacilityName())
                        .doroAddress(facility.getRoadNameDetailAddress())
                        .jibunAddress(facility.getAddressDetail())
                        .deptName("")
                        .deptContactNumber("")
                        .capacity(facility.getUsePossibleNumber())
                        .operationBeginDate("")
                        .operationEndDate("")
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
            safeKoreaResponse = getShelterInfo(i + 2, pageSize);
        }
        for (String areaCode : areaCodes) {
            HeatWaveShelterResponse heatWaveShelterResponse = getAdditionalInfo(areaCode);
            if (heatWaveShelterResponse.getHeatWaveShelter() == null) {
                continue;
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
        }
        heatWaveShelterRepository.saveAll(heatWaveShelters);
    }

    private HeatWaveShelterResponse getAdditionalInfo(String areaCode) throws URISyntaxException, JsonProcessingException, InterruptedException {
        WebClient webClient = WebClient.create();
        // 싹다 URI로 인코딩 해야 정상작동 함...
        String url = "https://apis.data.go.kr/1741000/HeatWaveShelter3/getHeatWaveShelterList3?serviceKey=" +
                serviceKey +
                "&pageNo=1&numOfRows=30&type=json&year=2024&areaCd=" + areaCode + "&equptype=";
        String result = webClient.get()
                .uri(new URI(url))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(result);
        HeatWaveShelterResponse response = objectMapper.readValue(result, HeatWaveShelterResponse.class);
        sleep(1500);
        return response;
    }

    private SafeKoreaResponse getShelterInfo(Integer pageIndex, Integer pageSize) throws InterruptedException {
        String url = "https://www.safekorea.go.kr/idsiSFK/sfk/cs/sfc/selectHtwRstrList.do";
        RequestPayload requestPayload = RequestPayload.builder()
                .searchInfo(RequestPayload.SearchInfo.builder()
                        .pageIndex(pageIndex)
                        .pageUnit(pageSize.toString())
                        .pageSize("10")
                        .firstIndex("1")
                        .lastIndex("1")
                        .recordCountPerPage("10")
                        .q_area_cd_1("27") // 대구광역시
                        .searchYear("2024")
                        .govAreaCode("27") // 대구광역시
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
        sleep(1500);
        return result;
    }
}
