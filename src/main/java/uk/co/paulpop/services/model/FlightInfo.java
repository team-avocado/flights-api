package uk.co.paulpop.services.model;

import lombok.Builder;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
@Entity
public class FlightInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer flightId;
    private String flightNo;
    private Integer departureTime;
    private Integer arrivalTime;
    private String gate;
    private String destination;
    private String status;

    public FlightInfo() {

    }

    public FlightInfo(Integer flightId, String flightNo, Integer departureTime, Integer arrivalTime, String gate, String destination, String status) {

    }

    public Integer getFlightId() {
        return flightId;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public Integer getDepartureTime() {
        return departureTime;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public String getGate() {
        return gate;
    }

    public String getDestination() {
        return destination;
    }

    public String getStatus() {
        return status;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public void setDepartureTime(Integer departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(Integer arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
