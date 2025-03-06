package com.example.flightplanner.service;

import com.example.flightplanner.dto.SeatDTO;
import com.example.flightplanner.dto.SeatPreferenceDTO;
import com.example.flightplanner.model.Seat;
import com.example.flightplanner.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public List<SeatDTO> getAllSeatsByFlightId(Long flightId) {
        return seatRepository.findByFlightId(flightId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<SeatDTO> getAvailableSeatsByFlightId(Long flightId) {
        return seatRepository.findByFlightIdAndIsOccupied(flightId, false).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<SeatDTO> recommendSeats(SeatPreferenceDTO preferences) {
        List<Seat> availableSeats = seatRepository.findByFlightIdAndIsOccupied(preferences.getFlightId(), false);

        // Prioritize seats based on user preferences
        List<Seat> recommendedSeats = new ArrayList<>();

        // Apply filters based on preferences
        if (preferences.isPreferWindow()) {
            recommendedSeats = seatRepository.findAvailableWindowSeats(preferences.getFlightId(), true);
        } else if (preferences.isPreferExtraLegroom()) {
            recommendedSeats = seatRepository.findAvailableLegroomSeats(preferences.getFlightId(), true);
        } else if (preferences.isPreferEmergencyExit()) {
            recommendedSeats = seatRepository.findAvailableEmergencyExitSeats(preferences.getFlightId(), true);
        } else {
            recommendedSeats = availableSeats;
        }

        // For multiple seats, try to find seats next to each other
        if (preferences.getNumberOfSeats() > 1 && preferences.isSeatsNextToEachOther()) {
            return findAdjacentSeats(recommendedSeats, preferences.getNumberOfSeats());
        }

        // Return the top N recommended seats based on preferences
        return recommendedSeats.stream()
                .limit(preferences.getNumberOfSeats())
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private List<SeatDTO> findAdjacentSeats(List<Seat> seats, int numberOfSeats) {
        // Group seats by row
        Map<String, List<Seat>> seatsByRow = seats.stream()
                .collect(Collectors.groupingBy(Seat::getSeatRow));

        // Look for adjacent seats in the same row
        for (Map.Entry<String, List<Seat>> entry : seatsByRow.entrySet()) {
            List<Seat> rowSeats = entry.getValue();

            // Sort seats by column
            rowSeats.sort(Comparator.comparing(Seat::getSeatColumn));

            // Check for consecutive seats
            for (int i = 0; i <= rowSeats.size() - numberOfSeats; i++) {
                boolean areConsecutive = true;
                for (int j = 0; j < numberOfSeats - 1; j++) {
                    char currentCol = rowSeats.get(i + j).getSeatColumn().charAt(0);
                    char nextCol = rowSeats.get(i + j + 1).getSeatColumn().charAt(0);

                    if (nextCol - currentCol != 1) {
                        areConsecutive = false;
                        break;
                    }
                }

                if (areConsecutive) {
                    return rowSeats.subList(i, i + numberOfSeats).stream()
                            .map(this::convertToDto)
                            .collect(Collectors.toList());
                }
            }
        }

        // If no adjacent seats found, return any available seats
        return seats.stream()
                .limit(numberOfSeats)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private SeatDTO convertToDto(Seat seat) {
        SeatDTO dto = new SeatDTO();
        dto.setId(seat.getId());
        dto.setSeatNumber(seat.getSeatNumber());
        dto.setSeatRow(seat.getSeatRow());
        dto.setSeatColumn(seat.getSeatColumn());
        dto.setWindow(seat.isWindow());
        dto.setEmergencyExit(seat.isEmergencyExit());
        dto.setHasExtraLegroom(seat.isHasExtraLegroom());
        dto.setOccupied(seat.isOccupied());
        return dto;
    }
}