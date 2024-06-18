package com.api.airport.models.entities;

import java.util.Date;

import com.api.airport.models.entities.pk.SeatReservationPK;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "SEAT_RESERVATION")
@Data
@IdClass(SeatReservationPK.class)
public class SeatReservation {

    @Id
    @Column(name = "FLIGHT_NUMBER")
    private Integer flightNumber;
    
    @Id
    @Column(name = "LEG_NUMBER")
    private Integer legNumber;
    
    @Id
    @Column(name = "LEG_DATE")
    private Date legDate;
    
    @Id
    @NotBlank(message = "Seat number is required")
    @Size(max = 4, message = "Seat number must have a maximum of 4 characters")
    @Column(name = "SEAT_NUMBER")
    private String seatNumber;
    
    @NotBlank(message = "Customer name is required")
    @Size(max = 18, message = "Customer name must have a maximum of 18 characters")
    @Column(name = "CUSTOMER_NAME")
    private String customerName;
    
    @NotBlank(message = "Customer phone is required")
    @Size(max = 12, message = "Customer phone must have a maximum of 12 characters")
    @Column(name = "CUSTOMER_PHONE")
    private String customerPhone;
    
    @ManyToOne
    @JoinColumn(name = "flightNumber", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_SEAT_RESERVATION_FLIGHT_LEG_INSTANCE"))
    @JoinColumn(name = "legNumber", insertable = false, updatable = false)
    @JoinColumn(name = "legDate", insertable = false, updatable = false)
    private FlightLegInstance flightLegInstance;
}

