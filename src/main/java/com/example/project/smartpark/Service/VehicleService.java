package com.example.project.smartpark.Service;

import com.example.project.smartpark.Dto.VehicleDto;
import com.example.project.smartpark.Exception.SmartParkException;
import com.example.project.smartpark.model.Vehicle;

import java.util.List;


public interface VehicleService {
    List<Vehicle> getAllVehicles();
    Vehicle createVehicle(VehicleDto vehicleDto) throws SmartParkException;
}
