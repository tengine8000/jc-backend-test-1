package com.virginholidays.backend.test.resource;

import com.virginholidays.backend.test.api.Flight;
import com.virginholidays.backend.test.service.FlightInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

/**
 * The FlightInfoResource unit tests
 *
 * @author Geoff Perks
 */
class FlightInfoResourceTest {

    private FlightInfoService flightInfoService;
    private FlightInfoResource flightInfoResource;

    @BeforeEach
    void setUp() {
        flightInfoService = mock(FlightInfoService.class);
        flightInfoResource = new FlightInfoResource(flightInfoService);
    }

    @Test
    void getResults_validDateWithResults_returnsOk() {
        LocalDate date = LocalDate.of(2024, 5, 21);
        Flight flight = new Flight(
                LocalTime.of(9, 0),
                "Antigua",
                "ANU",
                "VS033",
                List.of(DayOfWeek.TUESDAY)
        );
        List<Flight> flights = List.of(flight);

        when(flightInfoService.findFlightByDate(date))
                .thenReturn(CompletableFuture.completedFuture(Optional.of(flights)));

        CompletionStage<ResponseEntity<?>> responseStage = flightInfoResource.getResults("2024-05-21");
        ResponseEntity<?> response = responseStage.toCompletableFuture().join();

        assertEquals(OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
        assertEquals(flights, response.getBody());
    }

    @Test
    void getResults_validDateNoResults_returnsNoContent() {
        LocalDate date = LocalDate.of(2024, 5, 22);

        when(flightInfoService.findFlightByDate(date))
                .thenReturn(CompletableFuture.completedFuture(Optional.empty()));

        CompletionStage<ResponseEntity<?>> responseStage = flightInfoResource.getResults("2024-05-22");
        ResponseEntity<?> response = responseStage.toCompletableFuture().join();

        assertEquals(NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getResults_invalidDateFormat_returnsBadRequest() {
        CompletionStage<ResponseEntity<?>> responseStage = flightInfoResource.getResults("21-05-2024");
        ResponseEntity<?> response = responseStage.toCompletableFuture().join();

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(Objects.requireNonNull(response.getBody()).toString().contains("Invalid date format"));
    }
}

