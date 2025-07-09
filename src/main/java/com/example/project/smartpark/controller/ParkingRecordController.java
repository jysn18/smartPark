package com.example.project.smartpark.controller;

import com.example.project.smartpark.Dto.ParkingCostDetails;
import com.example.project.smartpark.Dto.ParkingRecordDto;
import com.example.project.smartpark.Exception.SmartParkException;
import com.example.project.smartpark.Service.ParkingRecordService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("parkingrecord")
public class ParkingRecordController {
    private static final Logger logger = LoggerFactory.getLogger(ParkingRecordController.class);

    private ParkingRecordService parkingRecordService;

    @PostMapping("/checkin")
    public ResponseEntity<?> parkingRecordCheckin(@RequestBody ParkingRecordDto parkingRecordDto) {
        logger.info("Checking in car in parking lot...");
        try{
            ParkingCostDetails parkingRecord = parkingRecordService.parkingRecordCheckin(parkingRecordDto);
            return ResponseEntity.ok().body(parkingRecord);
        } catch (SmartParkException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> parkingRecordCheckout(@RequestBody ParkingRecordDto parkingRecordDto) {
        logger.info("Checking out car in parking lot...");
        try{
            ParkingCostDetails parkingRecord = parkingRecordService.parkingRecordCheckout(parkingRecordDto);
            return ResponseEntity.ok().body(parkingRecord);
        } catch (SmartParkException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
