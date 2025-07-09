package com.example.project.smartpark.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class ParkingLot {

    @Id
    @Column(length = 50, unique = true, nullable = false)
    private String lotId;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private int capacity;
    private int occupiedSpace;

    @Column(nullable = false)
    private double costPerMinute;

    @OneToMany(mappedBy = "lotId")
    private List<ParkingRecord> parkingRecord;

}
