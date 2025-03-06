package com.example.flightplanner.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber;
    private String seatRow;
    private String seatColumn;

    private boolean isWindow;
    private boolean isEmergencyExit;
    private boolean hasExtraLegroom;
    private boolean isOccupied;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;
}