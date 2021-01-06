package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.jpa.domain.Specification;

public interface ShipFilterService {

    Specification<Ship> filterByName(String name);

    Specification<Ship> filterByPlanet(String planet);

    Specification<Ship> filterByType(ShipType shipType);

    Specification<Ship> filterByProdDate(Long after, Long before);

    Specification<Ship> filterByUsed(Boolean isUsed);

    Specification<Ship> filterBySpeed(Double minSpeed, Double maxSpeed);

    Specification<Ship> filterByCrewSize(Integer minCrewSize, Integer maxCrewSize);

    Specification<Ship> filterByRating(Double minRating, Double maxRating);

}
