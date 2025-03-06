package com.example.flightplanner.controller;

import com.example.flightplanner.dto.SeatDTO;
import com.example.flightplanner.dto.SeatPreferenceDTO;
import com.example.flightplanner.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@CrossOrigin(origins = "http://localhost:3000") // for React frontend
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<SeatDTO>> getAllSeatsByFlightId(@PathVariable Long flightId) {
        return ResponseEntity.ok(seatService.getAllSeatsByFlightId(flightId));
    }

    @GetMapping("/flight/{flightId}/available")
    public ResponseEntity<List<SeatDTO>> getAvailableSeatsByFlightId(@PathVariable Long flightId) {
        return ResponseEntity.ok(seatService.getAvailableSeatsByFlightId(flightId));
    }

    @PostMapping("/recommend")
    public ResponseEntity<List<SeatDTO>> recommendSeats(@RequestBody SeatPreferenceDTO preferences) {
        return ResponseEntity.ok(seatService.recommendSeats(preferences));
    }
}