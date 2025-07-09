package com.example.project.smartpark.Repository;

import com.example.project.smartpark.model.ParkingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingRecordRepository extends JpaRepository<ParkingRecord, Long> {
    Optional<ParkingRecord> findByLotIdAndLicensePlate(String lotId, String licensePlate);

    List<ParkingRecord> findByCheckOutTimeIsNullAndCheckInTimeBefore(LocalDateTime checkOutTime);
}
