package com.virginholidays.backend.test.resource;

import com.virginholidays.backend.test.api.Flight;
import com.virginholidays.backend.test.service.FlightInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.concurrent.CompletionStage;

import static org.springframework.http.CacheControl.noCache;
import static org.springframework.http.ResponseEntity.status;

/**
 * @author Geoff Perks
 *
 * The FlightInfoResource
 */
@RestController
public class FlightInfoResource {

    private final FlightInfoService flightInfoService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private final Logger LOGGER = LoggerFactory.getLogger(FlightInfoResource.class);

    /**
     * The constructor
     *
     * @param flightInfoService the flightInfoService
     */
    public FlightInfoResource(FlightInfoService flightInfoService) {
        this.flightInfoService = flightInfoService;
    }

    /**
     * Resource method for returning flight results
     *
     * @param date the chosen date
     * @return flights for the day of the chosen date
     */
    @RequestMapping(method = RequestMethod.GET, path = "/{date}/results")
    public CompletionStage<ResponseEntity<?>> getResults(@PathVariable("date") @NotEmpty String date) {

        return flightInfoService.findFlightByDate(getValidateDate(date)).thenApply(optionalFlights -> {
            if (optionalFlights.isEmpty()) {
                return status(HttpStatus.NO_CONTENT).cacheControl(noCache()).build();
            }

            List<Flight> results = optionalFlights.get();
            return status(HttpStatus.OK).cacheControl(noCache()).body(results);
        });
    }

    private LocalDate getValidateDate(String dateString){
        try {
            return LocalDate.parse(dateString, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            LOGGER.error("Invalid date format [{}]. Please use yyyy-MM-dd.", dateString);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format("Invalid date format [%s]. Please use yyyy-MM-dd.", dateString));
        }
    }
}
