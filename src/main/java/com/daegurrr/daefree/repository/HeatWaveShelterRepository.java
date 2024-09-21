package com.daegurrr.daefree.repository;

import com.daegurrr.daefree.dto.SearchResponse;
import com.daegurrr.daefree.entity.HeatWaveShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HeatWaveShelterRepository extends JpaRepository<HeatWaveShelter, Long> {
    @Query("SELECT new com.daegurrr.daefree.dto.SearchResponse$Position(h.id, h.latitude, h.longitude) FROM HeatWaveShelter h WHERE h.restType = :restType")
    List<SearchResponse.Position> findByRestType(@Param("restType") String restType);

    @Query("SELECT new com.daegurrr.daefree.dto.SearchResponse$Detail(h.id, h.restName, h.doroAddress, h.jibunAddress, h.deptContactNumber, " +
            "h.capacity, h.operationBeginDate, h.operationEndDate, h.openTime, h.closeTime, h.restType, h.latitude, h.longitude, h.isPublic) " +
            "FROM HeatWaveShelter h WHERE h.id = :id")
    SearchResponse.Detail findDetailById(@Param("id") Long id);

    @Query("SELECT new com.daegurrr.daefree.dto.SearchResponse$Detail(h.id, h.restName, h.doroAddress, h.jibunAddress, h.deptContactNumber, " +
            "h.capacity, h.operationBeginDate, h.operationEndDate, h.openTime, h.closeTime, h.restType, h.latitude, h.longitude, h.isPublic) " +
            "FROM HeatWaveShelter h WHERE h.id = :id")
    List<SearchResponse.Detail> findAllDetailById(@Param("id") Long id);

    @Query("SELECT new com.daegurrr.daefree.dto.SearchResponse$Detail(h.id, h.restName, h.doroAddress, h.jibunAddress, h.deptContactNumber, "
            + "h.capacity, h.operationBeginDate, h.operationEndDate, h.openTime, h.closeTime, h.restType, h.latitude, h.longitude, h.isPublic) "
            + "FROM HeatWaveShelter h "
            + "WHERE 6371000 * ACOS(COS(RADIANS(:latitude)) * COS(RADIANS(h.latitude)) "
            + "* COS(RADIANS(h.longitude) - RADIANS(:longitude)) + "
            + "SIN(RADIANS(:latitude)) * SIN(RADIANS(h.latitude))) <= :radius")
    List<SearchResponse.Detail> findSheltersWithinRadius(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("radius") double radius);
}
