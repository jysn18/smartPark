package com.example.project.smartpark.scheduler;

import com.example.project.smartpark.Repository.ParkingLotRepository;
import com.example.project.smartpark.Repository.ParkingRecordRepository;
import com.example.project.smartpark.Repository.VehicleRepository;
import com.example.project.smartpark.controller.AuthLoginController;
import com.example.project.smartpark.model.ParkingLot;
import com.example.project.smartpark.model.ParkingRecord;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class ParkingScheduler {
    private static final Logger logger = LoggerFactory.getLogger(ParkingScheduler.class);

    private ParkingRecordRepository recordRepo;
    private VehicleRepository vehicleRepo;
    private ParkingLotRepository parkingLotRepo;

    @Transactional
    @Scheduled(fixedRate = 60000) // Runs every minute
    public void autoCheckoutOldVehicles() {
        logger.info("checking park cars over 15 minutes...");
        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(15);
        List<ParkingRecord> oldRecords = recordRepo.findByCheckOutTimeIsNullAndCheckInTimeBefore(cutoff);

        oldRecords.forEach(record ->{
            Optional<ParkingLot> parkingLotOpt = parkingLotRepo.findByLotId(record.getLotId());
            if(parkingLotOpt.isPresent()) {
                var parkingLot = parkingLotOpt.get();
                parkingLot.setOccupiedSpace(parkingLot.getOccupiedSpace() - 1);
                parkingLotRepo.save(parkingLot);
            }
            logger.info("Deleting car record over 15 min parked, license plate: {}", record.getLicensePlate());
            recordRepo.delete(record);
        });
    }
}
