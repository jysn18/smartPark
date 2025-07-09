package com.example.project.smartpark.Repository;

import com.example.project.smartpark.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    Vehicle findByLicensePlate(String licensePlate);
}
