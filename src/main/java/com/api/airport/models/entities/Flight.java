package com.api.airport.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "FLIGHT")
@Data
public class Flight {
    
    @Id
    @Column(name = "FLIGHT_NUMBER")
    private Integer flightNumber;
    
    @NotBlank(message = "Airline is required")
    @Size(max = 10, message = "Airline must have a maximum of 10 characters")
    @Column(name = "AIRLINE")
    private String airline;

    @NotBlank(message = "Weekdays is required")
    @Size(max = 10, message = "Weekdays must have a maximum of 10 characters")
    @Column(name = "WEEKDAYS")
    private String weekdays;
    
    @ManyToOne
    @JoinColumn(name = "departureAirportCode", foreignKey = @ForeignKey(name = "FK_DEPARTURE_AIRPORT"))
    private Airport departureAirport;
    
    @ManyToOne
    @JoinColumn(name = "arrivalAirportCode", foreignKey = @ForeignKey(name = "FK_ARRIVAL_AIRPORT"))
    private Airport arrivalAirport;
}