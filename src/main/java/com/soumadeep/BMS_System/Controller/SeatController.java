package com.soumadeep.BMS_System.Controller;

import com.soumadeep.BMS_System.DTO.SeatRequest;
import com.soumadeep.BMS_System.Entity.Seat;
import com.soumadeep.BMS_System.Service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @PostMapping("/add")
    public ResponseEntity<Seat> addSeat(@RequestBody SeatRequest request) {
        Seat savedSeat = seatService.addSeat(request);
        return ResponseEntity.ok(savedSeat);
    }

    @GetMapping("/screen/{screenId}")
    public ResponseEntity<List<Seat>> getSeatsByScreen(@PathVariable Long screenId) {
        List<Seat> seats = seatService.getSeatsByScreen(screenId);
        return ResponseEntity.ok(seats);
    }

    @GetMapping("{id}")
    public ResponseEntity<Seat> getSeatById(@PathVariable Long id) {
        Seat seat = seatService.getSeatById(id);
        return ResponseEntity.ok(seat);
    }
}
