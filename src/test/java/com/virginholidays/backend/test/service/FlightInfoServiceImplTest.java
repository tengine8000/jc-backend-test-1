package com.virginholidays.backend.test.service;

import com.virginholidays.backend.test.api.Flight;
import com.virginholidays.backend.test.repository.FlightInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The FlightInfoServiceImpl unit tests
 *
 * @author Geoff Perks
 */
class FlightInfoServiceImplTest {

    private FlightInfoRepository flightInfoRepository;
    private FlightInfoServiceImpl service;

    @BeforeEach
    void setUp() {
        flightInfoRepository = mock(FlightInfoRepository.class);
        service = new FlightInfoServiceImpl(flightInfoRepository);
    }

    @Test
    void shouldReturnFlightsForGivenDate() throws ExecutionException, InterruptedException {
        LocalDate testDate = LocalDate.of(2025, 5, 21); // Wednesday
        Flight flight1 = new Flight(
                LocalTime.of(9, 0),
                "Barbados",
                "BGI",
                "VS029",
                Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)
        );

        Flight flight2 = new Flight(
                LocalTime.of(12, 0),
                "Las Vegas",
                "LAS",
                "VS043",
                List.of(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)
        );

        Flight flight3 = new Flight(
                LocalTime.of(10, 0),
                "Antigua",
                "ANU",
                "VS033",
                List.of(DayOfWeek.THURSDAY, DayOfWeek.SATURDAY)
        );

        Flight flight4 = new Flight(
                LocalTime.of(15, 0),
                "Cancun",
                "CUN",
                "VS093",
                List.of(DayOfWeek.TUESDAY)
        );

        List<Flight> allFlights = Arrays.asList(flight1, flight2, flight3, flight4);

        when(flightInfoRepository.findAll()).thenReturn(CompletableFuture.completedFuture(Optional.of(allFlights)));

        List<Flight> result = service.findFlightByDate(testDate).toCompletableFuture().get().orElseThrow();

        assertEquals(2, result.size(), "Should return 2 flights for Wednesday");

        // Confirm sorted order by departureTime
        assertEquals("VS029", result.get(0).flightNo());
        assertEquals("VS043", result.get(1).flightNo());
    }

    @Test
    void shouldReturnEmptyWhenNoFlightsOperateOnGivenDate() throws ExecutionException, InterruptedException {
        LocalDate sunday = LocalDate.of(2025, 5, 25); // Sunday

        Flight flight1 = new Flight(
                LocalTime.of(12, 0),
                "Las Vegas",
                "LAS",
                "VS043",
                Arrays.asList(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)
        );

        Flight flight2 = new Flight(
                LocalTime.of(10, 0),
                "Antigua",
                "ANU",
                "VS033",
                Arrays.asList(DayOfWeek.THURSDAY, DayOfWeek.SATURDAY)
        );

        List<Flight> allFlights = Arrays.asList(flight1, flight2);

        when(flightInfoRepository.findAll()).thenReturn(CompletableFuture.completedFuture(Optional.of(allFlights)));

        List<Flight> result = service.findFlightByDate(sunday).toCompletableFuture().get().orElseThrow();

        assertTrue(result.isEmpty(), "Should return no flights on Sunday");
    }

    @Test
    void shouldHandleEmptyOptionalGracefully() throws ExecutionException, InterruptedException {
        LocalDate date = LocalDate.of(2025, 5, 22); // any date

        when(flightInfoRepository.findAll()).thenReturn(CompletableFuture.completedFuture(Optional.empty()));

        Optional<List<Flight>> result = service.findFlightByDate(date).toCompletableFuture().get();

        assertTrue(result.isEmpty(), "Should handle empty Optional from repository");
    }
}