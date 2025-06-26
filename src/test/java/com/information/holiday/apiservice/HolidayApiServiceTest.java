package com.information.holiday.apiservice;

import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

class HolidayApiServiceTest {
    private static MockWebServer mockWebServer;
    private static HolidayApiService holidayApiService;

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        WebClient.Builder webClientBuilder = WebClient.builder();
        holidayApiService = new HolidayApiService(webClientBuilder, baseUrl);
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }
}
