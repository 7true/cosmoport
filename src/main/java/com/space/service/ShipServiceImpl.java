package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ShipServiceImpl implements ShipService {

    @Autowired
    private ShipRepository shipRepository;

    @Override
    public Page<Ship> getAll(Specification<Ship> specification, Pageable pageable) {
        return shipRepository.findAll(specification, pageable);
    }

    @Override
    public List<Ship> getAll(Specification<Ship> specification) {
        return shipRepository.findAll(specification);
    }

    @Override
    public Ship createShip(Ship ship) {
        if (ship.getName() == null ||
            ship.getPlanet() == null ||
            ship.getShipType() == null ||
            ship.getProdDate() == null ||
            ship.getSpeed() == null ||
            ship.getCrewSize() == null ||
            !isValidationOk(ship)
        ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (ship.getUsed() == null) {
            ship.setUsed(false);
        }
        System.out.println(ship.getProdDate());
        ship.setRating(calcRating(ship));
        shipRepository.saveAndFlush(ship);
        return ship;
    }

    @Override
    public Ship getById(Long id) {
        checkId(id);
        return shipRepository.findById(id).get();
    }

    @Override
    public Ship updateById(Long id, Ship newShip) {
        checkId(id);

        Ship currentShip = getById(id);

        if (newShip.getName() != null) {
            currentShip.setName(newShip.getName());
        }
        if (newShip.getPlanet() != null) {
            currentShip.setPlanet(newShip.getPlanet());
        }
        if (newShip.getShipType() != null) {
            currentShip.setShipType(newShip.getShipType());
        }
        if (newShip.getProdDate() != null) {
            currentShip.setProdDate(newShip.getProdDate());
        }
        if (newShip.getUsed() != null) {
            currentShip.setUsed(newShip.getUsed());
        }
        if (newShip.getSpeed() != null) {
            currentShip.setSpeed(newShip.getSpeed());
        }
        if (newShip.getCrewSize() != null) {
            currentShip.setCrewSize(newShip.getCrewSize());
        }

        if (newShip.getName() == null &&
                newShip.getPlanet() == null &&
                newShip.getShipType() == null &&
                newShip.getProdDate() == null &&
                newShip.getSpeed() == null &&
                newShip.getCrewSize() == null) return currentShip;

        currentShip.setRating(calcRating(currentShip));

        if (!isValidationOk(currentShip)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return shipRepository.saveAndFlush(currentShip);
    }

    @Override
    public void deleteById(Long id) {
        checkId(id);
        shipRepository.deleteById(id);
    }



    private void checkId(Long id) {
        if (id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (shipRepository.findById(id).equals(Optional.empty())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private Double calcRating(Ship ship) {
        double k = ship.getUsed() ? 0.5 : 1;
        double v = ship.getSpeed();
        int currentYear = 3019;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ship.getProdDate());
        int yearProd = calendar.get(Calendar.YEAR);
        Double rating = (80 * v * k) / (currentYear - yearProd + 1);
        rating = Math.round(rating * 100.0) / 100.0;
        return rating;
    }

    private boolean isValidationOk(Ship ship) {
        return ship.getName().length() > 0 &&
                ship.getName().length() <= 50 &&
                ship.getPlanet().length() > 0 &&
                ship.getPlanet().length() <= 50 &&
                ship.getSpeed() >= 0.01 &&
                ship.getSpeed() <= 0.99  &&
                ship.getCrewSize() >= 1 &&
                ship.getCrewSize() <= 9999 &&
                isProdDateValid(ship.getProdDate());
    }

    private boolean isProdDateValid(Date prodDate) {
        if (prodDate == null) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(prodDate);
        int year = calendar.get(Calendar.YEAR);
        if (year < 2800 || year > 3019) {
            return false;
        }
        return true;
    }

}
