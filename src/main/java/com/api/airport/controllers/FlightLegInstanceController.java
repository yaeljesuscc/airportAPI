package com.api.airport.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.airport.models.entities.FlightLegInstance;
import com.api.airport.models.entities.pk.FlightLegInstancePK;
import com.api.airport.services.FlightLegInstanceService;

@RestController
@RequestMapping("/flight-leg-instances")
public class FlightLegInstanceController {

        @Autowired
        private FlightLegInstanceService flightLegInstanceService;

        @GetMapping
        public ResponseEntity<List<FlightLegInstance>> getAllFlightLegInstances() {
                List<FlightLegInstance> flightLegInstances = flightLegInstanceService.findAll();
                return new ResponseEntity<>(flightLegInstances, HttpStatus.OK);
        }

        @GetMapping("/{flightNumber}/{legNumber}/{legDate}")
        public ResponseEntity<FlightLegInstance> getFlightLegInstanceById(
                        @PathVariable Integer flightNumber,
                        @PathVariable Integer legNumber,
                        @PathVariable String legDate) {
                FlightLegInstancePK id = FlightLegInstancePK.builder()
                                .flightNumber(flightNumber)
                                .legNumber(legNumber)
                                .legDate(java.sql.Date.valueOf(legDate))
                                .build();
                return flightLegInstanceService.findById(id)
                                .map(flightLegInstance -> new ResponseEntity<>(flightLegInstance, HttpStatus.OK))
                                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @PostMapping
        public ResponseEntity<FlightLegInstance> createFlightLegInstance(
                        @RequestBody FlightLegInstance flightLegInstance) {
                FlightLegInstance createdFlightLegInstance = flightLegInstanceService.save(flightLegInstance);
                return new ResponseEntity<>(createdFlightLegInstance, HttpStatus.CREATED);
        }

        @PutMapping("/{flightNumber}/{legNumber}/{legDate}")
        public ResponseEntity<FlightLegInstance> updateFlightLegInstance(
                        @PathVariable Integer flightNumber,
                        @PathVariable Integer legNumber,
                        @PathVariable String legDate,
                        @RequestBody FlightLegInstance flightLegInstance) {
                FlightLegInstancePK id = FlightLegInstancePK.builder()
                                .flightNumber(flightNumber)
                                .legNumber(legNumber)
                                .legDate(java.sql.Date.valueOf(legDate))
                                .build();
                return flightLegInstanceService.findById(id)
                                .map(existingFlightLegInstance -> {
                                        existingFlightLegInstance.setNumberOfAvailableSeats(
                                                        flightLegInstance.getNumberOfAvailableSeats());
                                        existingFlightLegInstance.setAirplaneId(flightLegInstance.getAirplaneId());
                                        existingFlightLegInstance
                                                        .setDepartureTime(flightLegInstance.getDepartureTime());
                                        existingFlightLegInstance.setArrivalTime(flightLegInstance.getArrivalTime());
                                        FlightLegInstance updatedFlightLegInstance = flightLegInstanceService
                                                        .save(existingFlightLegInstance);
                                        return new ResponseEntity<>(updatedFlightLegInstance, HttpStatus.OK);
                                })
                                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @DeleteMapping("/{flightNumber}/{legNumber}/{legDate}")
        public ResponseEntity<Void> deleteFlightLegInstance(
                        @PathVariable Integer flightNumber,
                        @PathVariable Integer legNumber,
                        @PathVariable String legDate) {
                FlightLegInstancePK id = FlightLegInstancePK.builder()
                                .flightNumber(flightNumber)
                                .legNumber(legNumber)
                                .legDate(java.sql.Date.valueOf(legDate))
                                .build();
                if (flightLegInstanceService.findById(id).isPresent()) {
                        flightLegInstanceService.deleteById(id);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
        }
}
