package com.virginholidays.backend.test.advice;

import com.virginholidays.backend.test.api.Flight;
import com.virginholidays.backend.test.resource.FlightInfoResource;
import com.virginholidays.backend.test.service.FlightInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FlightInfoResource.class)
class RestExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightInfoService flightInfoService;

    @Test
    void shouldReturn400WhenInvalidDatePassed() throws Exception {
        mockMvc.perform(get("/invalid-date-format/results"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", containsString("Invalid date format")))
                .andExpect(jsonPath("$.message", containsString("Invalid date format")));
    }
}
