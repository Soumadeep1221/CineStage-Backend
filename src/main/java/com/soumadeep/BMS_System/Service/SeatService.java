package com.soumadeep.BMS_System.Service;

import com.soumadeep.BMS_System.DTO.SeatRequest;
import com.soumadeep.BMS_System.Entity.Screen;
import com.soumadeep.BMS_System.Entity.Seat;
import com.soumadeep.BMS_System.Repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;
    private final ScreenService service;

    public Seat addSeat(SeatRequest request) {

        Screen screen= service.getScreenById(request.getScreenId());

        Seat seat = Seat.builder()
                .seatNumber(request.getSeatNumber())
                .row(request.getRow())
                .column(request.getColumn())
                .seatType(request.getSeatType())
                .screen(screen)
                .build();
        return seatRepository.save(seat);
    }

    public List<Seat> getSeatsByScreen(Long screenId){
        return seatRepository.findByScreenId(screenId);
    }

    public Seat getSeatById(Long id){
        return seatRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Seat not found with id:"+id));
    }
}
