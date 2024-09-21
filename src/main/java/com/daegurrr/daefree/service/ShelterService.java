package com.daegurrr.daefree.service;

import com.daegurrr.daefree.dto.shelter.ShelterResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.URISyntaxException;
import java.util.List;

public interface ShelterService {
    public List<ShelterResponse.Position> searchByType(String restType);
    public ShelterResponse.Detail searchDetailByPosition(Long id);

    public List<ShelterResponse.Detail> searchNearbyDetails(Double latitude, Double longitude);

    public void saveTest(int pageSize) throws URISyntaxException, JsonProcessingException, InterruptedException;
}
