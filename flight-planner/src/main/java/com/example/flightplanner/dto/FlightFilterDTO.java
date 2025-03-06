package com.example.flightplanner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightFilterDTO {
    private String destination;
    private LocalDate date;
    private LocalTime departureTimeFrom;
    private LocalTime departureTimeTo;
    private Double maxPrice;
}