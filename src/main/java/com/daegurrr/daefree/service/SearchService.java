package com.daegurrr.daefree.service;

import com.daegurrr.daefree.dto.HeatWaveShelterResponse;
import com.daegurrr.daefree.dto.SafeKoreaResponse;
import com.daegurrr.daefree.dto.SearchResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.URISyntaxException;
import java.util.List;

public interface SearchService {
    public List<SearchResponse.Position> searchByType(String restType);
    public SearchResponse.Detail searchDetailByPosition(Long id);

    public List<SearchResponse.Detail> searchNearbyDetails(Double latitude, Double longitude);

    public void saveTest(int pageSize) throws URISyntaxException, JsonProcessingException, InterruptedException;
}
