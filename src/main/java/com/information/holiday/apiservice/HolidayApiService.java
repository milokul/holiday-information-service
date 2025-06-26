package com.information.holiday.apiservice;

import com.information.holiday.dto.HolidayApiServiceResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class HolidayApiService {
    @Value("${holiday.api.base-url}")
    private String baseUrl;

    private final WebClient webClient;

    public HolidayApiService(
            WebClient.Builder webClientBuilder,
            @Value("${holiday.api.base-url}") String apiBaseUrl
    ) {
        this.webClient = webClientBuilder
                .baseUrl(apiBaseUrl)
                .build();
    }
    public Mono<List<HolidayApiServiceResponseDTO>> getPublicHolidays(String countryCode, String validFrom, String validTo) {

        // Build the URI first
        String uri = UriComponentsBuilder.fromPath("/PublicHolidays")
                .queryParam("countryIsoCode", countryCode)
                .queryParam("validFrom", validFrom)
                .queryParam("validTo", validTo)
                .build()
                .toUriString();


        if (log.isDebugEnabled()) {
            log.info("Calling an external service " + baseUrl + uri);
        }

        return webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(HolidayApiServiceResponseDTO.class)
                .collectList();
    }
}

