package shuttle;

import java.util.Map;

public class FareCalculator {

    static final int BASE_FARE_CENTS = 200;
    static final double INCLUDED_KM = 5;
    static final int PER_EXTRA_KM_CENTS = 50;

    static final Map<String, Double> DISCOUNTS = Map.of(
            "adult", 1.0,
            "student", 0.5,
            "senior", 0.5,
            "child", 0.0);

    // Original method still exists
    public static int fareCents(double distanceKm, String rider) {
        return fareCents(distanceKm, rider, false);
    }

    // New overloaded method with peak surcharge support
    public static int fareCents(double distanceKm, String rider, boolean peak) {

        if (distanceKm < 0) {
            throw new IllegalArgumentException("distance must be non-negative");
        }

        if (!DISCOUNTS.containsKey(rider)) {
            throw new IllegalArgumentException("unknown rider type: " + rider);
        }

        int extraKm = Math.max(0,
                (int) Math.ceil(distanceKm - INCLUDED_KM));

        int full = BASE_FARE_CENTS + extraKm * PER_EXTRA_KM_CENTS;

        if (peak) {
            full += 100;
        }

        return (int) Math.round(full * DISCOUNTS.get(rider));
    }
}