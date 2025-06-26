package com.information.holiday.holidayinformation;

import com.information.holiday.dto.HolidayApiServiceResponseDTO;
import com.information.holiday.exception.NoCommonDateException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class HolidayInformationCalculatorTest {
    private final HolidayInformationCalculator holidayInformationCalculator = new HolidayInformationCalculator();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void findObjectsWithLowestSameDate_WithCommonDates_ReturnsCorrectPair() throws ParseException {
        // Create test data
        Date date1 = sdf.parse("2024-01-01");
        Date date2 = sdf.parse("2024-01-01");
        Date commonDate = sdf.parse("2024-01-01");
        HolidayApiServiceResponseDTO dto1 = new HolidayApiServiceResponseDTO();
        dto1.setStartDate(date1);
        dto1.setEndDate(date2);
        dto1.setName(List.of());
        List<HolidayApiServiceResponseDTO> list1 = List.of(dto1);

        List<HolidayApiServiceResponseDTO> list2 = List.of(dto1);

        // Execute test
        Pair<HolidayApiServiceResponseDTO, HolidayApiServiceResponseDTO> result = holidayInformationCalculator.findObjectsWithLowestSameDate(
                list1,
                list2,
                HolidayApiServiceResponseDTO::getStartDate,
                HolidayApiServiceResponseDTO::getStartDate
        );

        // Verify results
        Assertions.assertNotNull(result);
        assertEquals(commonDate, result.first().getStartDate());
        assertEquals(commonDate, result.second().getStartDate());
    }

    @Test
    public void findObjectsWithLowestSameDate_NoCommonDates_ThrowsException() throws ParseException {
        // Create test data classes
        class Object1 {
            private final Date date;

            Object1(Date date) {
                this.date = date;
            }

            Date getDate() {
                return date;
            }
        }

        class Object2 {
            private final Date date;

            Object2(Date date) {
                this.date = date;
            }

            Date getDate() {
                return date;
            }
        }

        // Create test data with no common date

        List<Object1> list1 = Arrays.asList(
                new Object1(sdf.parse("2024-01-01")),
                new Object1(sdf.parse("2024-01-02"))
        );

        List<Object2> list2 = Arrays.asList(
                new Object2(sdf.parse("2024-01-03")),
                new Object2(sdf.parse("2024-01-04"))
        );

        // Execute and verify exception
        assertThrows(NoCommonDateException.class, () -> {
            holidayInformationCalculator.findObjectsWithLowestSameDate(
                    list1,
                    list2,
                    Object1::getDate,
                    Object2::getDate
            );
        });
    }

    @Test
    public void findObjectsWithLowestSameDate_MultipleCommonDates_ReturnsLowestDate() throws ParseException {
        // Create test data classes
        class Object1 {
            private final Date date;

            Object1(Date date) {
                this.date = date;
            }

            Date getDate() {
                return date;
            }
        }

        class Object2 {
            private final Date date;

            Object2(Date date) {
                this.date = date;
            }

            Date getDate() {
                return date;
            }
        }

        // Create test data with multiple common dates
        Date commonDate1 = sdf.parse("2024-02-01");
        Date commonDate2 = sdf.parse("2024-02-02");

        List<Object1> list1 = Arrays.asList(
                new Object1(commonDate1),
                new Object1(commonDate2)
        );

        List<Object2> list2 = Arrays.asList(
                new Object2(commonDate2),
                new Object2(commonDate1)
        );

        // Execute test
        Pair<Object1, Object2> result = holidayInformationCalculator.findObjectsWithLowestSameDate(
                list1,
                list2,
                Object1::getDate,
                Object2::getDate
        );

        // Verify results
        assertNotNull(result);
        assertEquals(commonDate1, result.first().getDate());
        assertEquals(commonDate1, result.second().getDate());
    }
}
