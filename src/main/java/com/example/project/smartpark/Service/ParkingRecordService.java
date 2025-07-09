package com.example.project.smartpark.Service;

import com.example.project.smartpark.Dto.ParkingCostDetails;
import com.example.project.smartpark.Dto.ParkingRecordDto;
import com.example.project.smartpark.Exception.SmartParkException;

public interface ParkingRecordService {
    ParkingCostDetails parkingRecordCheckin(ParkingRecordDto parkingRecordDto) throws SmartParkException;
    ParkingCostDetails parkingRecordCheckout(ParkingRecordDto parkingRecordDto) throws SmartParkException;
}
