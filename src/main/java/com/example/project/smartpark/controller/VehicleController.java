package com.example.project.smartpark.controller;

import com.example.project.smartpark.Dto.VehicleDto;
import com.example.project.smartpark.Exception.SmartParkException;
import com.example.project.smartpark.Service.VehicleService;
import com.example.project.smartpark.model.Vehicle;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vehicle")
@AllArgsConstructor
public class VehicleController {
    private static final Logger logger = LoggerFactory.getLogger(AuthLoginController.class);
    private VehicleService vehicleService;

    @PostMapping("/register")
    public ResponseEntity<?> createVehicle(@RequestBody VehicleDto vehicle) {
        logger.info("Registering vehicle information...");
        try {
            var createdVehicle = vehicleService.createVehicle(vehicle);
            return ResponseEntity.ok().body(createdVehicle);
        } catch (SmartParkException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Vehicle>> getAllVehicle(){
        logger.info("Getting all vehicle registered");
        return ResponseEntity.ok().body(vehicleService.getAllVehicles());
    }
}
