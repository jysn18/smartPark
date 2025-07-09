package com.example.project.smartpark.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ParkingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String lotId;
    private String licensePlate;

    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
}
