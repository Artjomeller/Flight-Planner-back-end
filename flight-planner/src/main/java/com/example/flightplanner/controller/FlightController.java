package com.example.flightplanner.controller;

import com.example.flightplanner.dto.FlightDTO;
import com.example.flightplanner.dto.FlightFilterDTO;
import com.example.flightplanner.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@CrossOrigin(origins = "http://localhost:3000") // for React frontend
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping
    public ResponseEntity<List<FlightDTO>> getAllFlights() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @PostMapping("/filter")
    public ResponseEntity<List<FlightDTO>> getFilteredFlights(@RequestBody FlightFilterDTO filterDTO) {
        return ResponseEntity.ok(flightService.getFilteredFlights(filterDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO> getFlightById(@PathVariable Long id) {
        FlightDTO flight = flightService.getFlightById(id);
        if (flight != null) {
            return ResponseEntity.ok(flight);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}