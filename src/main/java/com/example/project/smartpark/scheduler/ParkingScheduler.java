package com.example.project.smartpark.scheduler;

import com.example.project.smartpark.Repository.ParkingLotRepository;
import com.example.project.smartpark.Repository.ParkingRecordRepository;
import com.example.project.smartpark.Repository.VehicleRepository;
import com.example.project.smartpark.model.ParkingLot;
import com.example.project.smartpark.model.ParkingRecord;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class ParkingScheduler {

    private ParkingRecordRepository recordRepo;
    private VehicleRepository vehicleRepo;
    private ParkingLotRepository parkingLotRepo;

    @Transactional
    @Scheduled(fixedRate = 60000) // Runs every minute
    public void autoCheckoutOldVehicles() {
        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(15);
        List<ParkingRecord> oldRecords = recordRepo.findByCheckOutTimeIsNullAndCheckInTimeBefore(cutoff);

        oldRecords.forEach(record ->{
            Optional<ParkingLot> parkingLotOpt = parkingLotRepo.findByLotId(record.getLotId());
            if(parkingLotOpt.isPresent()) {
                var parkingLot = parkingLotOpt.get();
                parkingLot.setOccupiedSpace(parkingLot.getOccupiedSpace() - 1);
                parkingLotRepo.save(parkingLot);
            }
            recordRepo.delete(record);
        });
    }
}
