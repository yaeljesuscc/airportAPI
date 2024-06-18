package com.api.airport.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "AIRPORT")
@Data
public class Airport {
    
    @Id
    @NotBlank(message = "Airport code is required")
    @Size(max = 3, message = "Airport code must have a maximum of 3 characters")
    @Column(name = "AIRPORT_CODE")
    private String airportCode;
    
    @NotBlank(message = "Airport name is required")
    @Size(max = 15, message = "Airport name must have a maximum of 15 characters")
    @Column(name = "AIRPORT_NAME")
    private String airportName;
    
    @NotBlank(message = "City is required")
    @Size(max = 10, message = "City must have a maximum of 10 characters")
    @Column(name = "CITY")
    private String city;
    
    @NotBlank(message = "State is required")
    @Size(max = 10, message = "State must have a maximum of 10 characters")
    @Column(name = "STATE")
    private String state;
}
