package com.information.holiday.controller;

import com.information.holiday.apiservice.HolidayApiService;
import com.information.holiday.dto.HolidayApiServiceResponseDTO;
import com.information.holiday.dto.HolidayApiServiceResponseDTO.LocalizedName;
import com.information.holiday.dto.HolidayInformationResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class HolidayInformationControllerTest {

    @Mock
    private HolidayApiService holidayApiService;

    private WebTestClient webTestClient;

    private HolidayInformationController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new HolidayInformationController(holidayApiService);
        webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    void compareHolidays_WhenSameDateHolidays_ReturnsFirstMatchingDate() throws ParseException {
        // Given
        String countryCode1 = "US";
        String countryCode2 = "UK";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        var date1 = sdf.parse("2025-01-01");
        var date2 = sdf.parse("2025-07-04");

        HolidayApiServiceResponseDTO usHoliday =
                createDumpHolidayApiServiceResponseDTO("Independence Day", date2);
        HolidayApiServiceResponseDTO ukHoliday =
                createDumpHolidayApiServiceResponseDTO("Bank Holiday", date2);

        when(holidayApiService.getPublicHolidays(anyString(), anyString(), anyString()))
                .thenReturn(Mono.just(List.of(usHoliday)))
                .thenReturn(Mono.just(List.of(ukHoliday)));

        // When/Then
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/compare-holidays")
                        .queryParam("countryCode1", countryCode1)
                        .queryParam("countryCode2", countryCode2)
                        .queryParam("date", sdf.format(date1))
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(HolidayInformationResponseDTO.class)
                .value(response -> {
                        assert response.date().equals(sdf.format(date2));
                    assert response.name1().equals("Independence Day");
                    assert response.name2().equals("Bank Holiday");
                });
    }

    @Test
    void compareHolidays_WhenInvalidDate_ReturnsBadRequest() {
        // Given
        String countryCode1 = "US";
        String countryCode2 = "UK";
        String invalidDate = "invalid-date";

        // When/Then
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/compare-holidays")
                        .queryParam("countryCode1", countryCode1)
                        .queryParam("countryCode2", countryCode2)
                        .queryParam("date", invalidDate)
                        .build())
                .exchange()
                .expectStatus().isBadRequest();
    }

    private HolidayApiServiceResponseDTO createDumpHolidayApiServiceResponseDTO(String name, Date date) {
        LocalizedName localizedName = new LocalizedName();
        localizedName.setText(name);
        return new HolidayApiServiceResponseDTO(date, date, List.of(localizedName));
    }


}