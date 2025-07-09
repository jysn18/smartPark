package com.example.project.smartpark.Service.Impl;

import com.example.project.smartpark.Dto.ParkingAvailability;
import com.example.project.smartpark.Dto.ParkingCostDetails;
import com.example.project.smartpark.Dto.ParkingRecordDto;
import com.example.project.smartpark.Exception.SmartParkException;
import com.example.project.smartpark.Repository.ParkingLotRepository;
import com.example.project.smartpark.Repository.ParkingRecordRepository;
import com.example.project.smartpark.Repository.VehicleRepository;
import com.example.project.smartpark.Service.ParkingRecordService;
import com.example.project.smartpark.model.ParkingLot;
import com.example.project.smartpark.model.ParkingRecord;
import com.example.project.smartpark.model.Vehicle;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ParkingRecordServiceImpl implements ParkingRecordService {

    private ParkingRecordRepository parkingRecordRepository;
    private ParkingLotRepository parkingLotRepository;
    private VehicleRepository vehicleRepository;

    @Override
    @Transactional
    public ParkingCostDetails parkingRecordCheckin(ParkingRecordDto parkingRecordDto) throws SmartParkException {
        ParkingLot parkingLot = parkingLotRepository.findByLotId(parkingRecordDto.getParkingLotId()).orElseThrow(
                () -> new SmartParkException("Parking lotid entered is invalid"));

        Vehicle vehicle = vehicleRepository.findByLicensePlate(parkingRecordDto.getPlateNumber()).orElseThrow(
                () -> new SmartParkException("Vehicle not registered"));

        boolean isRecordPresent = parkingRecordRepository.findByLotIdAndLicensePlate(
                parkingRecordDto.getParkingLotId(), parkingRecordDto.getPlateNumber()).isPresent();

        if(parkingLot.getOccupiedSpace() == parkingLot.getCapacity())
            throw new SmartParkException("Can not park car, parking lot full");

        ParkingRecord parkingRecord = new ParkingRecord();
        ParkingCostDetails parkingCostDetails;

        if(!isRecordPresent) {
            parkingRecord.setLotId(parkingLot.getLotId());
            parkingRecord.setLicensePlate(vehicle.getLicensePlate());
            parkingRecord.setCheckInTime(LocalDateTime.now());

            parkingLot.setOccupiedSpace(parkingLot.getOccupiedSpace() + 1);
            parkingLotRepository.save(parkingLot);
            parkingRecord = parkingRecordRepository.save(parkingRecord);

            parkingCostDetails = generateParkingCostDetails(parkingRecord, vehicle);
        } else {
            throw new SmartParkException("Vehicle is already parked...");
        }
        return parkingCostDetails;
    }

    @Override
    @Transactional
    public ParkingCostDetails parkingRecordCheckout(ParkingRecordDto parkingRecordDto) throws SmartParkException {
        ParkingLot parkingLot = parkingLotRepository.findByLotId(parkingRecordDto.getParkingLotId()).orElseThrow(
                () -> new SmartParkException("Parking lotid entered is invalid"));
        Vehicle vehicle = vehicleRepository.findByLicensePlate(parkingRecordDto.getPlateNumber()).orElseThrow(
                () -> new SmartParkException("Vehicle not registered"));
        var isRecordPresent = parkingRecordRepository.findByLotIdAndLicensePlate(
                parkingRecordDto.getParkingLotId(), parkingRecordDto.getPlateNumber());

        ParkingCostDetails parkingCostDetails;
        if(isRecordPresent.isPresent()) {
            var existingRecord = isRecordPresent.get();
            parkingLot.setOccupiedSpace(parkingLot.getOccupiedSpace() - 1);
            parkingLotRepository.save(parkingLot);

            LocalDateTime checkoutTime = LocalDateTime.now();
            parkingCostDetails = generateParkingCostDetails(existingRecord, vehicle);
            parkingCostDetails.setParkingCost(calculateParkingCost(existingRecord, checkoutTime));
            parkingCostDetails.setCheckOutTime(checkoutTime);

            parkingRecordRepository.delete(existingRecord);
        } else {
            throw new SmartParkException("Vehicle is not in the parking lot, please check lot id...");
        }
        return parkingCostDetails;
    }

    private double calculateParkingCost(ParkingRecord record, LocalDateTime checkoutTime){
        record.setCheckOutTime(checkoutTime);
        return Duration.between(record.getCheckInTime(), checkoutTime).toMinutes();
    }

    private ParkingCostDetails generateParkingCostDetails(ParkingRecord existingRecord, Vehicle vehicle){
        ParkingCostDetails parkingCostDetails = new ParkingCostDetails();
        parkingCostDetails.setCheckInTime(existingRecord.getCheckInTime());
        parkingCostDetails.setOwnerName(vehicle.getOwnerName());
        parkingCostDetails.setLicencePlate(vehicle.getLicensePlate());

        return parkingCostDetails;
    }
}
