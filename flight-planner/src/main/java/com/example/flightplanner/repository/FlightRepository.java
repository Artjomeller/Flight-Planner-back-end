package com.example.flightplanner.repository;

import com.example.flightplanner.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByDestination(String destination);

    List<Flight> findByDepartureTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT f FROM Flight f WHERE f.destination = :destination AND f.departureTime BETWEEN :startDate AND :endDate AND f.price <= :maxPrice")
    List<Flight> findByFilters(
            @Param("destination") String destination,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("maxPrice") Double maxPrice
    );
}