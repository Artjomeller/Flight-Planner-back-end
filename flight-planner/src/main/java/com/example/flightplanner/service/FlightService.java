package com.example.flightplanner.service;

import com.example.flightplanner.dto.FlightDTO;
import com.example.flightplanner.dto.FlightFilterDTO;
import com.example.flightplanner.model.Flight;
import com.example.flightplanner.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public List<FlightDTO> getAllFlights() {
        return flightRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<FlightDTO> getFilteredFlights(FlightFilterDTO filterDTO) {
        // Convert date to LocalDateTime range
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;

        if (filterDTO.getDate() != null) {
            if (filterDTO.getDepartureTimeFrom() != null) {
                startDateTime = LocalDateTime.of(filterDTO.getDate(), filterDTO.getDepartureTimeFrom());
            } else {
                startDateTime = filterDTO.getDate().atStartOfDay();
            }

            if (filterDTO.getDepartureTimeTo() != null) {
                endDateTime = LocalDateTime.of(filterDTO.getDate(), filterDTO.getDepartureTimeTo());
            } else {
                endDateTime = filterDTO.getDate().plusDays(1).atStartOfDay();
            }
        }

        // Apply filters
        List<Flight> filteredFlights;
        if (filterDTO.getDestination() != null && !filterDTO.getDestination().isEmpty() &&
                startDateTime != null && endDateTime != null &&
                filterDTO.getMaxPrice() != null) {

            filteredFlights = flightRepository.findByFilters(
                    filterDTO.getDestination(),
                    startDateTime,
                    endDateTime,
                    filterDTO.getMaxPrice()
            );
        } else if (filterDTO.getDestination() != null && !filterDTO.getDestination().isEmpty()) {
            filteredFlights = flightRepository.findByDestination(filterDTO.getDestination());
        } else if (startDateTime != null && endDateTime != null) {
            filteredFlights = flightRepository.findByDepartureTimeBetween(startDateTime, endDateTime);
        } else {
            filteredFlights = flightRepository.findAll();
        }

        return filteredFlights.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public FlightDTO getFlightById(Long id) {
        return flightRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }

    private FlightDTO convertToDto(Flight flight) {
        FlightDTO dto = new FlightDTO();
        dto.setId(flight.getId());
        dto.setFlightNumber(flight.getFlightNumber());
        dto.setOrigin(flight.getOrigin());
        dto.setDestination(flight.getDestination());
        dto.setDepartureTime(flight.getDepartureTime());
        dto.setArrivalTime(flight.getArrivalTime());
        dto.setPrice(flight.getPrice());
        dto.setAirline(flight.getAirline());
        return dto;
    }
}