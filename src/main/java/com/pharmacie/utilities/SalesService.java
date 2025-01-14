package com.pharmacie.utilities;

import java.time.*;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class SalesService {

    public Map<String, Double> getSalesAmountByDayOfWeek(Employee employe) {
        // Get current date
        LocalDate today = LocalDate.now();
        // Get the start of the current week (Monday)
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        // Get the end of the current week (Sunday)
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);

        // Filter the purchases made in the current week
        List<Purchase> weeklyPurchases = employe.getPurchases().stream()
                .filter(purchase -> !purchase.getCreatedAt().toLocalDate().isBefore(startOfWeek) && 
                                     !purchase.getCreatedAt().toLocalDate().isAfter(endOfWeek))
                .collect(Collectors.toList());

        // Group the purchases by day of the week and calculate the sum of the amount for each day
        Map<String, Double> salesByDay = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            // Get the name of the day in French (Lun, Mar, Mer, etc.)
            String dayName = day.getDisplayName(TextStyle.SHORT, Locale.FRENCH);
            // Filter purchases for the current day
            double totalForDay = weeklyPurchases.stream()
                    .filter(purchase -> purchase.getCreatedAt().toLocalDate().getDayOfWeek() == day)
                    .mapToDouble(Purchase::getTotal) // Sum the total of each purchase
                    .sum();

            // Add the total sales amount for the day to the map
            salesByDay.put(dayName, totalForDay);
        }

        return salesByDay;
    }
}
