package com.information.holiday.holidayinformation;

import com.information.holiday.exception.NoCommonDateException;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HolidayInformationCalculator {
    public <T, U> Pair<T, U> findObjectsWithLowestSameDate(List<T> list1, List<U> list2,
                                                           Function<T, Date> dateExtractor1,
                                                           Function<U, Date> dateExtractor2) {

        // Get all dates from both lists
        Set<Date> dates1 = list1.stream()
                .map(dateExtractor1)
                .collect(Collectors.toSet());

        Set<Date> dates2 = list2.stream()
                .map(dateExtractor2)
                .collect(Collectors.toSet());

        // Find the lowest common date
        Date lowestCommonDate = dates1.stream()
                .filter(dates2::contains)
                .min(Date::compareTo)
                .orElseThrow(() -> new NoCommonDateException("No common dates found"));

        // Find objects with the lowest common date
        T obj1 = list1.stream()
                .filter(obj -> dateExtractor1.apply(obj).equals(lowestCommonDate))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Object from list1 not found"));

        U obj2 = list2.stream()
                .filter(obj -> dateExtractor2.apply(obj).equals(lowestCommonDate))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Object from list2 not found"));

        return new Pair<>(obj1, obj2);
    }
}
