package com.chust.servis.gps;

import android.location.Location;

public class DistanceCalculator {

    public static double calculate(
        double lat1, double lon1,
        double lat2, double lon2) {

        float[] result = new float[1];

        Location.distanceBetween(
            lat1,
            lon1,
            lat2,
            lon2,
            result
        );

        return result[0] / 1000.0;
    }

    public static double calculateMeters(
        double lat1, double lon1,
        double lat2, double lon2) {

        float[] result = new float[1];

        Location.distanceBetween(
            lat1,
            lon1,
            lat2,
            lon2,
            result
        );

        return result[0];
    }
}
