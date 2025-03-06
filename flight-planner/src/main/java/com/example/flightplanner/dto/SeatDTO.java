package com.example.flightplanner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatDTO {
    private Long id;
    private String seatNumber;
    private String seatRow;
    private String seatColumn;
    private boolean isWindow;
    private boolean isEmergencyExit;
    private boolean hasExtraLegroom;
    private boolean isOccupied;
}