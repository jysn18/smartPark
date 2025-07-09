package com.example.project.smartpark.Service.Impl;

import com.example.project.smartpark.Dto.CarType;
import com.example.project.smartpark.Dto.ParkingRecordDto;
import com.example.project.smartpark.Exception.SmartParkException;
import com.example.project.smartpark.Repository.ParkingLotRepository;
import com.example.project.smartpark.Repository.ParkingRecordRepository;
import com.example.project.smartpark.Repository.VehicleRepository;
import com.example.project.smartpark.model.ParkingLot;
import com.example.project.smartpark.model.ParkingRecord;
import com.example.project.smartpark.model.Vehicle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ParkingRecordServiceImplTest {

    @Mock
    private ParkingRecordRepository parkingRecordRepository;

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private ParkingRecordServiceImpl parkingRecordServiceImpl;

    @Test
    void itShouldTestMethodParkingRecordCheckinSuccess(){
        ParkingRecordDto parkingRecordDto = new ParkingRecordDto();
        ParkingLot parkingLot =getParkingLotData();
        Optional<ParkingLot> parkingLotOpt = Optional.of(parkingLot);
        Vehicle vehicle = getVehicleData();
        Optional<Vehicle> vehicleOpt = Optional.of(vehicle);

        ParkingRecord parkingRecord = getParkingRecordData();
        Optional<ParkingRecord> parkingRecordOpt = Optional.empty();

        when(parkingLotRepository.findByLotId(any())).thenReturn(parkingLotOpt);
        when(vehicleRepository.findByLicensePlate(any())).thenReturn(vehicleOpt);
        when(parkingRecordRepository.findByLicensePlate(any())).thenReturn(parkingRecordOpt);
        when(parkingRecordRepository.save(any())).thenReturn(parkingRecord);

        try {
            var result = parkingRecordServiceImpl.parkingRecordCheckin(parkingRecordDto);
            Assertions.assertEquals(result.getOwnerName(), vehicle.getOwnerName());
            Assertions.assertEquals(result.getLicencePlate(), vehicle.getLicensePlate());
            Assertions.assertEquals(result.getCheckInTime(), parkingRecord.getCheckInTime());
        }catch (SmartParkException ignored){}
    }

    @Test
    void whenParkingLotIsNotFoundMethodRecordCheckinMustThrowError(){
        ParkingRecordDto parkingRecordDto = new ParkingRecordDto();
        when(parkingLotRepository.findByLotId(any())).thenReturn(Optional.empty());
        try {
            parkingRecordServiceImpl.parkingRecordCheckin(parkingRecordDto);
        } catch (SmartParkException e){
            Assertions.assertEquals("Parking lotid entered is invalid", e.getMessage());
        }
    }

    @Test
    void whenVehicleIsNotFoundMethodRecordCheckinMustThrowError(){
        ParkingLot parkingLot =getParkingLotData();
        Optional<ParkingLot> parkingLotOpt = Optional.of(parkingLot);

        ParkingRecordDto parkingRecordDto = new ParkingRecordDto();
        when(parkingLotRepository.findByLotId(any())).thenReturn(parkingLotOpt);
        when(vehicleRepository.findByLicensePlate(any())).thenReturn(Optional.empty());
        try {
            parkingRecordServiceImpl.parkingRecordCheckin(parkingRecordDto);
        } catch (SmartParkException e){
            Assertions.assertEquals("Vehicle not registered", e.getMessage());
        }
    }

    @Test
    void whenVehicleIsAlreadyParkedMethodParkingRecordCheckinMustThrowError(){
        ParkingRecordDto parkingRecordDto = new ParkingRecordDto();
        ParkingLot parkingLot =getParkingLotData();
        Optional<ParkingLot> parkingLotOpt = Optional.of(parkingLot);
        Vehicle vehicle = getVehicleData();
        Optional<Vehicle> vehicleOpt = Optional.of(vehicle);

        ParkingRecord parkingRecord = getParkingRecordData();
        Optional<ParkingRecord> parkingRecordOpt = Optional.of(parkingRecord);

        when(parkingLotRepository.findByLotId(any())).thenReturn(parkingLotOpt);
        when(vehicleRepository.findByLicensePlate(any())).thenReturn(vehicleOpt);
        when(parkingRecordRepository.findByLicensePlate(any())).thenReturn(parkingRecordOpt);

        try {
            parkingRecordServiceImpl.parkingRecordCheckin(parkingRecordDto);
        } catch (SmartParkException e){
            Assertions.assertEquals("Vehicle is already parked...", e.getMessage());
        }
    }

    @Test
    void whenCapacityIsFullParkingRecordCheckinMustThrowError(){
        ParkingRecordDto parkingRecordDto = new ParkingRecordDto();
        ParkingLot parkingLot =getParkingLotData();
        parkingLot.setCapacity(10);
        parkingLot.setOccupiedSpace(10);
        Optional<ParkingLot> parkingLotOpt = Optional.of(parkingLot);
        Vehicle vehicle = getVehicleData();
        Optional<Vehicle> vehicleOpt = Optional.of(vehicle);

        ParkingRecord parkingRecord = getParkingRecordData();
        Optional<ParkingRecord> parkingRecordOpt = Optional.empty();

        when(parkingLotRepository.findByLotId(any())).thenReturn(parkingLotOpt);
        when(vehicleRepository.findByLicensePlate(any())).thenReturn(vehicleOpt);
        when(parkingRecordRepository.findByLicensePlate(any())).thenReturn(parkingRecordOpt);

        try {
            parkingRecordServiceImpl.parkingRecordCheckin(parkingRecordDto);
        } catch (SmartParkException e){
            Assertions.assertEquals("Can not park car, parking lot full", e.getMessage());
        }
    }

    @Test
    void itShouldTestVehicleCheckoutSuccess(){
        ParkingRecordDto parkingRecordDto = new ParkingRecordDto();
        ParkingLot parkingLot =getParkingLotData();
        Optional<ParkingLot> parkingLotOpt = Optional.of(parkingLot);
        Vehicle vehicle = getVehicleData();
        Optional<Vehicle> vehicleOpt = Optional.of(vehicle);

        ParkingRecord parkingRecord = getParkingRecordData();
        Optional<ParkingRecord> parkingRecordOpt = Optional.of(parkingRecord);

        when(parkingLotRepository.findByLotId(any())).thenReturn(parkingLotOpt);
        when(vehicleRepository.findByLicensePlate(any())).thenReturn(vehicleOpt);
        when(parkingRecordRepository.findByLotIdAndLicensePlate(any(), any())).thenReturn(parkingRecordOpt);
        when(parkingRecordRepository.save(any())).thenReturn(parkingRecord);

        try {
            var result = parkingRecordServiceImpl.parkingRecordCheckout(parkingRecordDto);
            Assertions.assertEquals(vehicle.getOwnerName(), result.getOwnerName());
            Assertions.assertEquals(vehicle.getLicensePlate(), result.getLicencePlate());
            Assertions.assertEquals(parkingRecord.getCheckInTime(), result.getCheckInTime());
            Assertions.assertEquals(5, result.getParkingCost());
        }catch (SmartParkException ignored){}
    }

    @Test
    void whenParkingLotIsNotFoundMethodRecordCheckOutMustThrowError(){
        ParkingRecordDto parkingRecordDto = new ParkingRecordDto();
        when(parkingLotRepository.findByLotId(any())).thenReturn(Optional.empty());
        try {
            parkingRecordServiceImpl.parkingRecordCheckin(parkingRecordDto);
        } catch (SmartParkException e){
            Assertions.assertEquals("Parking lotid entered is invalid", e.getMessage());
        }
    }

    @Test
    void whenVehicleIsNotFoundMethodRecordCheckOutMustThrowError(){
        ParkingLot parkingLot =getParkingLotData();
        Optional<ParkingLot> parkingLotOpt = Optional.of(parkingLot);

        ParkingRecordDto parkingRecordDto = new ParkingRecordDto();
        when(parkingLotRepository.findByLotId(any())).thenReturn(parkingLotOpt);
        when(vehicleRepository.findByLicensePlate(any())).thenReturn(Optional.empty());
        try {
            parkingRecordServiceImpl.parkingRecordCheckin(parkingRecordDto);
        } catch (SmartParkException e){
            Assertions.assertEquals("Vehicle not registered", e.getMessage());
        }
    }

    @Test
    void whenParkingRecordIsEmptyMethodParkingRecordCheckoutShouldThrowError(){
        ParkingRecordDto parkingRecordDto = new ParkingRecordDto();
        ParkingLot parkingLot =getParkingLotData();
        Optional<ParkingLot> parkingLotOpt = Optional.of(parkingLot);
        Vehicle vehicle = getVehicleData();
        Optional<Vehicle> vehicleOpt = Optional.of(vehicle);

        when(parkingLotRepository.findByLotId(any())).thenReturn(parkingLotOpt);
        when(vehicleRepository.findByLicensePlate(any())).thenReturn(vehicleOpt);
        when(parkingRecordRepository.findByLotIdAndLicensePlate(any(), any())).thenReturn(Optional.empty());

        try {
            parkingRecordServiceImpl.parkingRecordCheckout(parkingRecordDto);
        }catch (SmartParkException e){
            Assertions.assertEquals("Vehicle is not in the parking lot, please check lot id...", e.getMessage());
        }
    }

    private ParkingLot getParkingLotData(){
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setLotId("PXGzPuzw7BF10mRWP5qUhRIvzc3ko1uZB6FFzVC1HSRE0LNJn8");
        parkingLot.setLocation("Megamall");
        parkingLot.setCostPerMinute(2);
        parkingLot.setCapacity(10);
        parkingLot.setOccupiedSpace(1);
        return parkingLot;
    }

    private Vehicle getVehicleData(){
        Vehicle vehicle = new Vehicle();
        vehicle.setOwnerName("Jayson");
        vehicle.setType(CarType.CAR);
        vehicle.setLicensePlate("NCB-4611");
        return vehicle;
    }

    private ParkingRecord getParkingRecordData(){
        ParkingRecord parkingRecord = new ParkingRecord();
        parkingRecord.setLicensePlate("NCB-4611");
        parkingRecord.setCheckInTime(LocalDateTime.now().minusMinutes(5));
        parkingRecord.setLotId("PXGzPuzw7BF10mRWP5qUhRIvzc3ko1uZB6FFzVC1HSRE0LNJn8");
        return parkingRecord;
    }
}
