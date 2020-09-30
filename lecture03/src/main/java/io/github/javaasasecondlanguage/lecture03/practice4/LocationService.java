package io.github.javaasasecondlanguage.lecture03.practice4;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


public class LocationService {
    private final List<Place> places;

    public LocationService(List<Place> places) {
        this.places = places;
    }

    public Optional<Place> findClosestByTag(Location userLocation, String tag) {
        places.stream().filter(x -> x.tags().contains(tag))
                .min(x -> userLocation.distanceTo(x.location))
                .get()
                .name();
        throw new RuntimeException("Not implemented");
    }

    public String mostCommonTag() {
        places.stream().filter(x -> x.tags())
                ;
        throw new RuntimeException("Not implemented");
    }
}

record Place(
        String name,
        Location location,
        List<String> tags
) { }

record Location(double lat, double lon) {
    // Thanks gosh we have internet!
    public double distanceTo(Location other) {
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(other.lat - lat);
        double lonDistance = Math.toRadians(other.lon - this.lon);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                   + Math.cos(Math.toRadians(this.lat)) * Math.cos(Math.toRadians(other.lat))
                     * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters
        return Math.abs(distance);
    }
}
