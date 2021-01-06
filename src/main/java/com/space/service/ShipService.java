package com.space.service;

import com.space.model.Ship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ShipService {

    Page<Ship> getAll(Specification<Ship> specification, Pageable pageable);

    List<Ship> getAll(Specification<Ship> specification);

    Ship createShip(Ship ship);

    Ship getById(Long id);

    Ship updateById(Long id, Ship updateShip);

    void deleteById(Long id);
}

