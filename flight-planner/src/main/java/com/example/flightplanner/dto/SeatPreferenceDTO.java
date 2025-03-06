package com.example.flightplanner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatPreferenceDTO {
    private Long flightId;
    private boolean preferWindow;
    private boolean preferExtraLegroom;
    private boolean preferEmergencyExit;
    private int numberOfSeats;
    private boolean seatsNextToEachOther;
}