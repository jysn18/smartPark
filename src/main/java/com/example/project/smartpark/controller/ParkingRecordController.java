package com.example.project.smartpark.controller;

import com.example.project.smartpark.Dto.ParkingCostDetails;
import com.example.project.smartpark.Dto.ParkingRecordDto;
import com.example.project.smartpark.Exception.SmartParkException;
import com.example.project.smartpark.Service.ParkingRecordService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("parkingrecord")
public class ParkingRecordController {

    private ParkingRecordService parkingRecordService;

    @PostMapping("/checkin")
    public ResponseEntity<?> parkingRecordCheckin(@RequestBody ParkingRecordDto parkingRecordDto) {
        try{
            ParkingCostDetails parkingRecord = parkingRecordService.parkingRecordCheckin(parkingRecordDto);
            return ResponseEntity.ok().body(parkingRecord);
        } catch (SmartParkException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> parkingRecordCheckout(@RequestBody ParkingRecordDto parkingRecordDto) {
        try{
            ParkingCostDetails parkingRecord = parkingRecordService.parkingRecordCheckout(parkingRecordDto);
            return ResponseEntity.ok().body(parkingRecord);
        } catch (SmartParkException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
