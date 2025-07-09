package com.example.project.smartpark.Dto;

import lombok.Data;

@Data
public class ParkingAvailability {
    private String lotId;
    private String location;
    private int capacity;
    private int availableSlotCount;
}
