package com.example.project.smartpark.Dto;

import lombok.Data;

@Data
public class ParkingLotDto {
    private String lotId;
    private String location;
    private int capacity;
    private double costPerMinute;
}
