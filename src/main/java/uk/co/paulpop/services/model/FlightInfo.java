package uk.co.paulpop.services.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlightInfo {

    private final Integer flightId;
    private final String flightNo;
    private final Integer departureTime;
    private final Integer arrivalTime;
    private final String gate;
    private final String destination;
    private final String status;
}
