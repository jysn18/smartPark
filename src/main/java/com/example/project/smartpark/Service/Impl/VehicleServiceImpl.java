package com.example.project.smartpark.Service.Impl;

import com.example.project.smartpark.Dto.VehicleDto;
import com.example.project.smartpark.Exception.SmartParkException;
import com.example.project.smartpark.Repository.VehicleRepository;
import com.example.project.smartpark.Service.VehicleService;
import com.example.project.smartpark.model.Vehicle;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private VehicleRepository vehicleRepository;

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle createVehicle(VehicleDto vehicleDto) throws SmartParkException {
        if(!vehicleDto.getLicensePlate().matches("^[a-zA-Z0-9-]+$"))
            throw new SmartParkException("Invalid License plate, it must be alphanumeric and dash only");

        if(!vehicleDto.getOwnerName().matches("[a-zA-Z0-9 ]+$"))
            throw new SmartParkException("Invalid name, special character not allowed");

        if(vehicleRepository.findByLicensePlate(vehicleDto.getLicensePlate()).isPresent())
            throw new SmartParkException("Vehicle already registered");

        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(vehicleDto.getLicensePlate());
        vehicle.setType(vehicleDto.getType());
        vehicle.setOwnerName(vehicleDto.getOwnerName());
        return vehicleRepository.save(vehicle);
    }
}
