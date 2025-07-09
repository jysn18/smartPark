package com.example.project.smartpark.controller;

import com.example.project.smartpark.Dto.ParkingLotDto;
import com.example.project.smartpark.Exception.SmartParkException;
import com.example.project.smartpark.Service.ParkingLotService;
import com.example.project.smartpark.model.ParkingLot;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("parkinglot")
@AllArgsConstructor
public class ParkingLotController {

    private ParkingLotService parkingLotService;

    @PostMapping("/register")
    public ResponseEntity<?> createVehicle(@RequestBody ParkingLotDto parkingLot) {
        ParkingLot createdVehicle = parkingLotService.createParkingLot(parkingLot);
        return ResponseEntity.ok().body(createdVehicle);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllParkingLot(){
        return ResponseEntity.ok().body(parkingLotService.getAllParkingLot());
    }

    @GetMapping("/details")
    public ResponseEntity<?> getParkingLotById(@RequestParam String id) {
        try{
            return ResponseEntity.ok().body(parkingLotService.getParkingLotById(id));
        } catch (SmartParkException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
