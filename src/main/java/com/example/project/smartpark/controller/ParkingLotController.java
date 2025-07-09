package com.example.project.smartpark.controller;

import com.example.project.smartpark.Dto.ParkingLotDto;
import com.example.project.smartpark.Exception.SmartParkException;
import com.example.project.smartpark.Service.ParkingLotService;
import com.example.project.smartpark.model.ParkingLot;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("parkinglot")
@AllArgsConstructor
public class ParkingLotController {
    private static final Logger logger = LoggerFactory.getLogger(ParkingLotController.class);

    private ParkingLotService parkingLotService;

    @PostMapping("/register")
    public ResponseEntity<?> createParkingLot(@RequestBody ParkingLotDto parkingLot) {
        logger.info("Registering parking lot information");
        try {
            ParkingLot createdVehicle = parkingLotService.createParkingLot(parkingLot);
            return ResponseEntity.ok().body(createdVehicle);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllParkingLot(){
        logger.info("Getting all parking lot information");
        return ResponseEntity.ok().body(parkingLotService.getAllParkingLot());
    }

    @GetMapping("/details")
    public ResponseEntity<?> getParkingLotById(@RequestParam String id) {
        logger.info("Getting parking lot by ID: {}", id);
        try{
            return ResponseEntity.ok().body(parkingLotService.getParkingLotById(id));
        } catch (SmartParkException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/availability")
    public ResponseEntity<?> getParkingAvailabilityDetails(@RequestParam String id){
        logger.info("Getting Parking Details");
        try{
            return ResponseEntity.ok().body(parkingLotService.getParkingAvailabilityDetails(id));
        } catch (SmartParkException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
