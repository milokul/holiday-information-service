package com.information.holiday.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HolidayApiServiceResponseDTO {

    @JsonProperty("startDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonProperty("endDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @JsonProperty("name")
    private List<LocalizedName> name;

    // Nested class for localized names
    public static class LocalizedName {
        @JsonProperty("language")
        private String language;

        @JsonProperty("text")
        private String text;

        // Getters and Setters
        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}

