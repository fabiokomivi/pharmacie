package com.pharmacie.utilities;

import java.time.*;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

import com.pharmacie.models.Purchase;

public class SalesServicelist {

    public Map<String, List<Purchase>> getSalesByDayOfWeek(Employee employe) {
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

        // Group the purchases by day of the week
        Map<String, List<Purchase>> salesByDay = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            // Get the name of the day in French (Lun, Mar, Mer, etc.)
            String dayName = day.getDisplayName(TextStyle.SHORT, Locale.FRENCH);
            // Filter purchases for the current day
            List<Purchase> purchasesForDay = weeklyPurchases.stream()
                    .filter(purchase -> purchase.getCreatedAt().toLocalDate().getDayOfWeek() == day)
                    .collect(Collectors.toList());

            // Add the list to the map (even if it's empty for a given day)
            salesByDay.put(dayName, purchasesForDay);
        }

        return salesByDay;
    }
}
