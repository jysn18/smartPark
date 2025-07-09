package com.example.project.smartpark.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ParkingCostDetails {
    String OwnerName;
    String licencePlate;
    double parkingCost;
    LocalDateTime checkInTime;
    LocalDateTime checkOutTime;
}
