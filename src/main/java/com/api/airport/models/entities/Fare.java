package com.api.airport.models.entities;

import com.api.airport.models.entities.pk.FarePK;

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
@Table(name = "FARE")
@Data
@IdClass(FarePK.class)
public class Fare {
    
    @Id
    @Column(name = "FLIGHT_NUMBER")
    private Integer flightNumber;
    
    @Id
    @NotBlank(message = "Fare code is required")
    @Size(max = 1, message = "Restrictions must have a maximun of 1 characteres")
    @Column(name = "FARE_CODE")
    private String fareCode;
    
    @Column(name = "AMOUNT")
    private Double amount;
    
    @NotBlank(message = "Restrictions is required")
    @Size(max = 3, message = "Restrictions must have a maximun of 3 characteres")
    @Column(name = "RESTRICTIONS")
    private String restrictions;
    
    @ManyToOne
    @JoinColumn(name = "flightNumber", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_FARE_FLIGHT"))
    private Flight flight;
}
