package com.thedebuggers.backend.common.util;

public class GeometryUtil {
    public static Location calculate(double baseLatitude, double baseLongitude, double distance,
                                     double bearing) {
        double radianLatitude = toRadian(baseLatitude);
        double radianLongitude = toRadian(baseLongitude);
        double radianAngle = toRadian(bearing);
        double distanceRadius = distance / 6371.01;

        double latitude = Math.asin(sin(radianLatitude) * cos(distanceRadius) +
                cos(radianLatitude) * sin(distanceRadius) * cos(radianAngle));
        double longitude = radianLongitude + Math.atan2(sin(radianAngle) * sin(distanceRadius) *
                cos(radianLatitude), cos(distanceRadius) - sin(radianLatitude) * sin(latitude));

        longitude = normalizeLongitude(longitude);
        return new Location(toDegree(latitude), toDegree(longitude));
    }

    private static double toRadian(double coordinate) {
        return coordinate * Math.PI / 180.0;
    }

    private static double toDegree(double coordinate) {
        return coordinate * 180.0 / Math.PI;
    }

    private static double sin(double coordinate) {
        return Math.sin(coordinate);
    }

    private static double cos(double coordinate) {
        return Math.cos(coordinate);
    }

    private static double normalizeLongitude(double longitude) {
        return (longitude + 540) % 360 - 180;
    }
}
