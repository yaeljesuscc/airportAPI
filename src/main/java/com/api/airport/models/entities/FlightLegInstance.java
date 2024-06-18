package com.api.airport.models.entities;

import java.util.Date;

import com.api.airport.models.entities.pk.FlightLegInstancePK;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "FLIGHT_LEG_INSTANCE")
@Data
@IdClass(FlightLegInstancePK.class)
public class FlightLegInstance {

    @Id
    @Column(name = "FLIGHT_NUMBER")
    private Integer flightNumber;
    
    @Id
    @Column(name = "LEG_NUMBER")
    private Integer legNumber;
    
    @Id
    @Column(name = "LEG_DATE")
    private Date legDate;
    
    @Column(name = "NUMBER_OF_AVAILABLE_SEATS")
    private Integer numberOfAvailableSeats;
    
    @Column(name = "AIRPLANE_ID")
    private Integer airplaneId;
    
    @Column(name = "DEPARTURE_TIME")
    private Date departureTime;
    
    @Column(name = "ARRIVAL_TIME")
    private Date arrivalTime;
    
    @ManyToOne
    @JoinColumn(name = "flightNumber", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_FLIGHT_LEG_INSTANCE_FLIGHT"))
    private Flight flight;
}


