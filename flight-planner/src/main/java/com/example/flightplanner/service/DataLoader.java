package com.example.flightplanner.service;

import com.example.flightplanner.model.Flight;
import com.example.flightplanner.model.Seat;
import com.example.flightplanner.repository.FlightRepository;
import com.example.flightplanner.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private SeatRepository seatRepository;

    private Random random = new Random();

    @Override
    public void run(String... args) {
        // Create sample flights
        createSampleFlights();
    }

    private void createSampleFlights() {
        // Create destinations
        String[] destinations = {"London", "Paris", "Berlin", "Rome", "Madrid", "Amsterdam", "Barcelona", "Vienna"};
        String[] origins = {"Tallinn", "Helsinki", "Stockholm", "Riga"};
        String[] airlines = {"AirBaltic", "Finnair", "Lufthansa", "Ryanair", "Wizz Air"};

        // Create flights for the next 30 days
        LocalDateTime now = LocalDateTime.now();

        List<Flight> flights = new ArrayList<>();

        for (int day = 0; day < 30; day++) {
            for (int i = 0; i < 5; i++) { // 5 flights per day
                String destination = destinations[random.nextInt(destinations.length)];
                String origin = origins[random.nextInt(origins.length)];
                String airline = airlines[random.nextInt(airlines.length)];

                // Create random departure time for the day
                int hour = 6 + random.nextInt(15); // Flights between 6 AM and 9 PM
                int minute = random.nextInt(60);

                LocalDateTime departureTime = now.plusDays(day)
                        .withHour(hour)
                        .withMinute(minute)
                        .withSecond(0);

                // Flight duration between 1 and 4 hours
                int durationHours = 1 + random.nextInt(3);
                int durationMinutes = random.nextInt(60);

                LocalDateTime arrivalTime = departureTime.plusHours(durationHours).plusMinutes(durationMinutes);

                // Price between 50 and 300
                double price = 50 + random.nextInt(250);

                Flight flight = new Flight();
                flight.setFlightNumber(airline.substring(0, 2).toUpperCase() + (100 + random.nextInt(900)));
                flight.setOrigin(origin);
                flight.setDestination(destination);
                flight.setDepartureTime(departureTime);
                flight.setArrivalTime(arrivalTime);
                flight.setPrice(price);
                flight.setAirline(airline);

                flights.add(flight);
            }
        }

        flightRepository.saveAll(flights);

        // Create seats for each flight
        for (Flight flight : flights) {
            createSeatsForFlight(flight);
        }
    }

    private void createSeatsForFlight(Flight flight) {
        // Define aircraft configuration
        int numRows = 30;
        String[] columns = {"A", "B", "C", "D", "E", "F"};

        List<Seat> seats = new ArrayList<>();

        for (int row = 1; row <= numRows; row++) {
            for (String column : columns) {
                Seat seat = new Seat();
                seat.setSeatRow(String.valueOf(row));
                seat.setSeatColumn(column);
                seat.setSeatNumber(row + column);
                seat.setFlight(flight);

                // Set seat properties
                // Window seats are A and F
                seat.setWindow(column.equals("A") || column.equals("F"));

                // Emergency exit rows (typically rows 12 and 13)
                seat.setEmergencyExit(row == 12 || row == 13);

                // Extra legroom (first row, emergency exit rows, and last row)
                seat.setHasExtraLegroom(row == 1 || row == 12 || row == 13 || row == numRows);

                // Randomly mark some seats as occupied (30% chance)
                seat.setOccupied(random.nextDouble() < 0.3);

                seats.add(seat);
            }
        }

        seatRepository.saveAll(seats);
    }
}