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

import com.api.airport.models.entities.SeatReservation;
import com.api.airport.models.entities.pk.SeatReservationPK;
import com.api.airport.services.SeatReservationService;

@RestController
@RequestMapping("/seat-reservations")
public class SeatReservationController {

        @Autowired
        private SeatReservationService seatReservationService;

        @GetMapping
        public ResponseEntity<List<SeatReservation>> getAllSeatReservations() {
                List<SeatReservation> seatReservations = seatReservationService.findAll();
                return new ResponseEntity<>(seatReservations, HttpStatus.OK);
        }

        @GetMapping("/{flightNumber}/{legNumber}/{legDate}/{seatNumber}")
        public ResponseEntity<SeatReservation> getSeatReservationById(
                        @PathVariable Integer flightNumber,
                        @PathVariable Integer legNumber,
                        @PathVariable String legDate,
                        @PathVariable String seatNumber) {
                SeatReservationPK id = SeatReservationPK.builder()
                                .flightNumber(flightNumber)
                                .legNumber(legNumber)
                                .legDate(java.sql.Date.valueOf(legDate))
                                .seatNumber(seatNumber)
                                .build();
                return seatReservationService.findById(id)
                                .map(seatReservation -> new ResponseEntity<>(seatReservation, HttpStatus.OK))
                                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @PostMapping
        public ResponseEntity<SeatReservation> createSeatReservation(@RequestBody SeatReservation seatReservation) {
                SeatReservation createdSeatReservation = seatReservationService.save(seatReservation);
                return new ResponseEntity<>(createdSeatReservation, HttpStatus.CREATED);
        }

        @PutMapping("/{flightNumber}/{legNumber}/{legDate}/{seatNumber}")
        public ResponseEntity<SeatReservation> updateSeatReservation(
                        @PathVariable Integer flightNumber,
                        @PathVariable Integer legNumber,
                        @PathVariable String legDate,
                        @PathVariable String seatNumber,
                        @RequestBody SeatReservation seatReservation) {
                SeatReservationPK id = SeatReservationPK.builder()
                                .flightNumber(flightNumber)
                                .legNumber(legNumber)
                                .legDate(java.sql.Date.valueOf(legDate))
                                .seatNumber(seatNumber)
                                .build();
                return seatReservationService.findById(id)
                                .map(existingSeatReservation -> {
                                        existingSeatReservation.setCustomerName(seatReservation.getCustomerName());
                                        existingSeatReservation.setCustomerPhone(seatReservation.getCustomerPhone());
                                        SeatReservation updatedSeatReservation = seatReservationService
                                                        .save(existingSeatReservation);
                                        return new ResponseEntity<>(updatedSeatReservation, HttpStatus.OK);
                                })
                                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @DeleteMapping("/{flightNumber}/{legNumber}/{legDate}/{seatNumber}")
        public ResponseEntity<Void> deleteSeatReservation(
                        @PathVariable Integer flightNumber,
                        @PathVariable Integer legNumber,
                        @PathVariable String legDate,
                        @PathVariable String seatNumber) {
                SeatReservationPK id = SeatReservationPK.builder()
                                .flightNumber(flightNumber)
                                .legNumber(legNumber)
                                .legDate(java.sql.Date.valueOf(legDate))
                                .seatNumber(seatNumber)
                                .build();
                if (seatReservationService.findById(id).isPresent()) {
                        seatReservationService.deleteById(id);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
        }
}
