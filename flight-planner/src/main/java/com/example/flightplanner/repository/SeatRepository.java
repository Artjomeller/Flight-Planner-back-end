package com.example.flightplanner.repository;

import com.example.flightplanner.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByFlightId(Long flightId);

    List<Seat> findByFlightIdAndIsOccupied(Long flightId, boolean isOccupied);

    @Query("SELECT s FROM Seat s WHERE s.flight.id = :flightId AND s.isOccupied = false AND s.isWindow = :isWindow")
    List<Seat> findAvailableWindowSeats(@Param("flightId") Long flightId, @Param("isWindow") boolean isWindow);

    @Query("SELECT s FROM Seat s WHERE s.flight.id = :flightId AND s.isOccupied = false AND s.hasExtraLegroom = :hasExtraLegroom")
    List<Seat> findAvailableLegroomSeats(@Param("flightId") Long flightId, @Param("hasExtraLegroom") boolean hasExtraLegroom);

    @Query("SELECT s FROM Seat s WHERE s.flight.id = :flightId AND s.isOccupied = false AND s.isEmergencyExit = :isEmergencyExit")
    List<Seat> findAvailableEmergencyExitSeats(@Param("flightId") Long flightId, @Param("isEmergencyExit") boolean isEmergencyExit);
}