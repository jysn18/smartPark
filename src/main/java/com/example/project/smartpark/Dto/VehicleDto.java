package com.example.project.smartpark.Dto;

import lombok.Data;

@Data
public class VehicleDto {
    private String licensePlate;
    private CarType type;
    private String ownerName;
}
