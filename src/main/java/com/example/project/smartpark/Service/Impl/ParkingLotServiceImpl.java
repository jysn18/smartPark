package com.example.project.smartpark.Service.Impl;

import com.example.project.smartpark.Dto.ParkingLotDto;
import com.example.project.smartpark.Exception.SmartParkException;
import com.example.project.smartpark.Repository.ParkingLotRepository;
import com.example.project.smartpark.Service.ParkingLotService;
import com.example.project.smartpark.model.ParkingLot;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ParkingLotServiceImpl implements ParkingLotService {

    ParkingLotRepository parkingLotRepository;

    @Override
    public ParkingLot getParkingLotById(String id) throws SmartParkException {
        return parkingLotRepository.findByLotId(id).orElseThrow(
                () -> new SmartParkException("Parking lotid entered is invalid"));
    }

    @Override
    public List<ParkingLot> getAllParkingLot() {
        return parkingLotRepository.findAll();
    }

    @Override
    public ParkingLot createParkingLot(ParkingLotDto parkingLotDto) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setLotId(RandomStringUtils.randomAlphanumeric(50));
        parkingLot.setCapacity(parkingLotDto.getCapacity());
        parkingLot.setLocation(parkingLotDto.getLocation());
        parkingLot.setOccupiedSpace(0);
        parkingLot.setCostPerMinute(parkingLotDto.getCostPerMinute());

        return parkingLotRepository.save(parkingLot);
    }
}
