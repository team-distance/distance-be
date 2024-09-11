package io.festival.distance.domain.eventmatching.dto.request;

public record EventMatchRequest(
    String school,
    String telNum,
    String preferCharacter
) {

}
