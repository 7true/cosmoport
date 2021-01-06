package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ShipFilterServiceImpl implements ShipFilterService {
    @Override
    public Specification<Ship> filterByName(String name) {
       return(root, query, criteriaBuilder) -> {
           if (name == null) {
               return null;
           }
           return criteriaBuilder.like(root.get("name"), "%" + name + "%");
       };
    }

    @Override
    public Specification<Ship> filterByPlanet(String planet) {
        return (root, quiery, criteriaBuilder) -> {
            if (planet == null) {
                return null;
            }
            return criteriaBuilder.like(root.get("planet"), "%" + planet + "%");
        };
    }

    @Override
    public Specification<Ship> filterByType(ShipType shipType) {
        return (root, quiery, criteriaBuilder) -> {
            if (shipType == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("shipType"), shipType);
        };
    }

    @Override
    public Specification<Ship> filterByProdDate(Long after, Long before) {
        return (root, query, criteriaBuilder) -> {
            if (after == null && before == null) {
                return null;
            }
            if (before == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("prodDate"), new Date(after));
            }
            if (after == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("prodDate"), new Date(before));
            }
            return criteriaBuilder.between(root.get("prodDate"), new Date(after), new Date(before));
        };
    }

    @Override
    public Specification<Ship> filterByUsed(Boolean isUsed) {
        return (root, query, criteriaBuilder) -> {
            if (isUsed == null) {
                return null;
            }
            if (isUsed) {
                return criteriaBuilder.isTrue(root.get("isUsed"));
            } else {
                return criteriaBuilder.isFalse(root.get("isUsed"));
            }
        };
    }

    @Override
    public Specification<Ship> filterBySpeed(Double minSpeed, Double maxSpeed) {
        return (root, query, criteriaBuilder) -> {
            if (minSpeed == null && maxSpeed == null) {
                return null;
            }
            if (minSpeed == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("speed"), maxSpeed);
            }
            if (maxSpeed == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("speed"), minSpeed);
            }

            return criteriaBuilder.between(root.get("speed"), minSpeed, maxSpeed);
        };
    }

    @Override
    public Specification<Ship> filterByCrewSize(Integer minCrewSize, Integer maxCrewSize) {
        return (root, query, criteriaBuilder) -> {
            if (minCrewSize == null && maxCrewSize == null) {
                return null;
            }
            if (minCrewSize == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("crewSize"), maxCrewSize);
            }
            if (maxCrewSize == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("crewSize"), minCrewSize);
            }
            return criteriaBuilder.between(root.get("crewSize"), minCrewSize, maxCrewSize);
        };
    }

    @Override
    public Specification<Ship> filterByRating(Double minRating, Double maxRating) {
        return (Specification<Ship>) (root, query, criteriaBuilder) -> {
            if (minRating == null && maxRating == null) {
                return null;
            }
            if (minRating == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("rating"), maxRating);
            }
            if (maxRating == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), minRating);
            }
            return criteriaBuilder.between(root.get("rating"), minRating, maxRating);
        };
    }
}
