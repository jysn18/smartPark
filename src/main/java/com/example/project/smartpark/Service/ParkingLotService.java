package com.example.project.smartpark.Service;

import com.example.project.smartpark.Dto.ParkingAvailability;
import com.example.project.smartpark.Dto.ParkingLotDto;
import com.example.project.smartpark.Exception.SmartParkException;
import com.example.project.smartpark.model.ParkingLot;

import java.util.List;

public interface ParkingLotService {
    ParkingLot getParkingLotById(String id) throws SmartParkException;
    List<ParkingLot> getAllParkingLot();
    ParkingLot createParkingLot(ParkingLotDto parkingLot) throws SmartParkException;
    ParkingAvailability getParkingAvailabilityDetails(String lotId) throws SmartParkException;
}
