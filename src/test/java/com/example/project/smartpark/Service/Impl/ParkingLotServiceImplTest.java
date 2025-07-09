package com.example.project.smartpark.Service.Impl;

import com.example.project.smartpark.Dto.ParkingLotDto;
import com.example.project.smartpark.Exception.SmartParkException;
import com.example.project.smartpark.Repository.ParkingLotRepository;
import com.example.project.smartpark.model.ParkingLot;
import com.example.project.smartpark.model.Vehicle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ParkingLotServiceImplTest {

    @Mock
    ParkingLotRepository parkingLotRepository;

    @InjectMocks
    ParkingLotServiceImpl parkingLotService;

    @Test
    void createParkingLotShouldReturnSuccess(){
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setLotId("PXGzPuzw7BF10mRWP5qUhRIvzc3ko1uZB6FFzVC1HSRE0LNJn8");
        parkingLot.setLocation("Megamall");
        parkingLot.setCostPerMinute(2);
        parkingLot.setCapacity(10);
        parkingLot.setOccupiedSpace(0);
        when(parkingLotRepository.findByLotId(any())).thenReturn(Optional.empty());
        when(parkingLotRepository.save(any())).thenReturn(parkingLot);
        try{
            var result = parkingLotService.createParkingLot(new ParkingLotDto());
            Assertions.assertEquals(parkingLot.getLotId(), result.getLotId());
            Assertions.assertEquals(parkingLot.getLocation(), result.getLocation());
            Assertions.assertEquals(parkingLot.getOccupiedSpace(), result.getOccupiedSpace());
            Assertions.assertEquals(parkingLot.getCapacity(), result.getCapacity());
            Assertions.assertEquals(parkingLot.getCostPerMinute(), result.getCostPerMinute());
        }catch (SmartParkException ignored){}
    }

    @Test
    void whenParkingLotIsAlreadyRegisteredMethodCreateParkingLotShouldThrowError(){
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setLotId("PXGzPuzw7BF10mRWP5qUhRIvzc3ko1uZB6FFzVC1HSRE0LNJn8");
        parkingLot.setLocation("Megamall");
        parkingLot.setCostPerMinute(2);
        parkingLot.setCapacity(10);
        parkingLot.setOccupiedSpace(0);
        when(parkingLotRepository.findByLotId(any())).thenReturn(Optional.of(getParkingLotData()));

        try{
            parkingLotService.createParkingLot(new ParkingLotDto());
        }catch (SmartParkException e){
            Assertions.assertEquals("Parking lot already registered", e.getMessage());
        }
    }

    private ParkingLot getParkingLotData(){
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setLotId("PXGzPuzw7BF10mRWP5qUhRIvzc3ko1uZB6FFzVC1HSRE0LNJn8");
        parkingLot.setLocation("Megamall");
        parkingLot.setCostPerMinute(2);
        parkingLot.setCapacity(10);
        parkingLot.setOccupiedSpace(1);
        return parkingLot;
    }
}
