package io.festival.distance.domain.councilgps.dto.request;

public record CouncilGpsRequest(
    double councilLatitude,
    double councilLongitude,
    String location
) {

}
