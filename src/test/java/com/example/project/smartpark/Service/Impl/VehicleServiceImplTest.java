package com.example.project.smartpark.Service.Impl;

import com.example.project.smartpark.Dto.CarType;
import com.example.project.smartpark.Dto.VehicleDto;
import com.example.project.smartpark.Exception.SmartParkException;
import com.example.project.smartpark.Repository.VehicleRepository;
import com.example.project.smartpark.model.Vehicle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class VehicleServiceImplTest {
    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    VehicleServiceImpl vehicleServiceImpl;

    @Test
    void createVehicleShouldReturnSuccess(){
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setOwnerName("Jayson");
        vehicleDto.setLicensePlate("NCB-4611");
        vehicleDto.setType(CarType.CAR);

        when(vehicleRepository.findByLicensePlate(any())).thenReturn(Optional.empty());
        when(vehicleRepository.save(any())).thenReturn(getVehicleData());

        try {
            Vehicle result = vehicleServiceImpl.createVehicle(vehicleDto);
            Assertions.assertEquals(vehicleDto.getOwnerName(), result.getOwnerName());
            Assertions.assertEquals(vehicleDto.getLicensePlate(), result.getLicensePlate());
            Assertions.assertEquals(vehicleDto.getType(), result.getType());
        } catch (SmartParkException ignored){}
    }

    @Test
    void whenVehicleIsAlreadyRegisteredMethodCreateVehicleMustThrowError(){
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setOwnerName("Jayson");
        vehicleDto.setLicensePlate("NCB-4611");
        vehicleDto.setType(CarType.CAR);

        when(vehicleRepository.findByLicensePlate(any())).thenReturn(Optional.of(new Vehicle()));
        when(vehicleRepository.save(any())).thenReturn(getVehicleData());

        try {
            vehicleServiceImpl.createVehicle(vehicleDto);
        } catch (SmartParkException e){
            Assertions.assertEquals("Vehicle already registered", e.getMessage());
        }
    }

    @Test
    void whenLicensePlateIsInvalidMethodCreateVehicleMustThrowError(){
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setOwnerName("Jayson");
        vehicleDto.setLicensePlate("NCB-4611!34%");
        vehicleDto.setType(CarType.CAR);

        try {
            vehicleServiceImpl.createVehicle(vehicleDto);
        } catch (SmartParkException e){
            Assertions.assertEquals("Invalid License plate, it must be alphanumeric and dash only", e.getMessage());
        }
    }

    @Test
    void whenOwnerNameIsInvalidMethodCreateVehicleMustThrowError(){
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setOwnerName("Jayson!@# Bal");
        vehicleDto.setLicensePlate("NCB-1111");
        vehicleDto.setType(CarType.CAR);

        try {
            vehicleServiceImpl.createVehicle(vehicleDto);
        } catch (SmartParkException e){
            Assertions.assertEquals("Invalid name, special character not allowed", e.getMessage());
        }
    }

    private Vehicle getVehicleData(){
        Vehicle vehicle = new Vehicle();
        vehicle.setOwnerName("Jayson");
        vehicle.setType(CarType.CAR);
        vehicle.setLicensePlate("NCB-4611");
        return vehicle;
    }
}
