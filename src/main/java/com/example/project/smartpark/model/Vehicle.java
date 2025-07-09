package com.example.project.smartpark.model;

import com.example.project.smartpark.Dto.CarType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Vehicle {
    @Id
    @Column(nullable = false)
    private String licensePlate;

    @Column(nullable = false)
    private CarType type;

    @Column(nullable = false)
    private String ownerName;
}
