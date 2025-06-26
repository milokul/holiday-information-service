
package com.information.holiday.controller;


import com.information.holiday.dto.HolidayApiServiceResponseDTO;
import com.information.holiday.apiservice.HolidayApiService;
import com.information.holiday.dto.HolidayInformationResponseDTO;
import com.information.holiday.holidayinformation.HolidayInformationCalculator;
import com.information.holiday.holidayinformation.Pair;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

@RestController
public class HolidayInformationController {
    private final HolidayApiService holidayApiService;

    public HolidayInformationController(HolidayApiService holidayApiService) {
        this.holidayApiService = holidayApiService;
    }

    @GetMapping("/compare-holidays")
    public Mono<HolidayInformationResponseDTO> compareHolidays(
            @RequestParam String countryCode1,
            @RequestParam String countryCode2,
           @Valid @RequestParam @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}") @DateTimeFormat(pattern = "yyyy-MM-dd") String date
    ) {
        DateCalculator dateCalculator = new DateCalculator();
        HolidayInformationCalculator holidayInformationCalculator = new HolidayInformationCalculator();
        String validFrom = dateCalculator.addOneDay(date);
        String validTo = dateCalculator.addOneYear(validFrom);
        return Mono.zip(
                holidayApiService.getPublicHolidays(countryCode1, validFrom, validTo),
                holidayApiService.getPublicHolidays(countryCode2, validFrom, validTo)
        ).map(tuple -> {
            List<HolidayApiServiceResponseDTO> holidays1 = tuple.getT1();
            List<HolidayApiServiceResponseDTO> holidays2 = tuple.getT2();
            Pair<HolidayApiServiceResponseDTO, HolidayApiServiceResponseDTO> pair = holidayInformationCalculator.findObjectsWithLowestSameDate(holidays1, holidays2, HolidayApiServiceResponseDTO::getStartDate, HolidayApiServiceResponseDTO::getStartDate);
            return new HolidayInformationResponseDTO(dateCalculator.format(pair.first().getStartDate()), this.getName(pair.first()), this.getName(pair.second()));
        });
    }

    private String getName(HolidayApiServiceResponseDTO dto) {
        return dto.getName().getFirst().getText();
    }
}