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

import com.api.airport.models.entities.Fare;
import com.api.airport.models.entities.pk.FarePK;
import com.api.airport.services.FareService;

@RestController
@RequestMapping("/fares")
public class FareController {

        @Autowired
        private FareService fareService;

        @GetMapping
        public ResponseEntity<List<Fare>> getAllFares() {
                List<Fare> fares = fareService.findAll();
                return new ResponseEntity<>(fares, HttpStatus.OK);
        }

        @GetMapping("/{flightNumber}/{fareCode}")
        public ResponseEntity<Fare> getFareById(@PathVariable Integer flightNumber, @PathVariable String fareCode) {
                FarePK id = FarePK.builder()
                                .flightNumber(flightNumber)
                                .fareCode(fareCode)
                                .build();
                return fareService.findById(id)
                                .map(fare -> new ResponseEntity<>(fare, HttpStatus.OK))
                                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @PostMapping
        public ResponseEntity<Fare> createFare(@RequestBody Fare fare) {
                Fare createdFare = fareService.save(fare);
                return new ResponseEntity<>(createdFare, HttpStatus.CREATED);
        }

        @PutMapping("/{flightNumber}/{fareCode}")
        public ResponseEntity<Fare> updateFare(@PathVariable Integer flightNumber, @PathVariable String fareCode,
                        @RequestBody Fare fare) {
                FarePK id = FarePK.builder()
                                .flightNumber(flightNumber)
                                .fareCode(fareCode)
                                .build();
                return fareService.findById(id)
                                .map(existingFare -> {
                                        existingFare.setAmount(fare.getAmount());
                                        existingFare.setRestrictions(fare.getRestrictions());
                                        Fare updatedFare = fareService.save(existingFare);
                                        return new ResponseEntity<>(updatedFare, HttpStatus.OK);
                                })
                                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @DeleteMapping("/{flightNumber}/{fareCode}")
        public ResponseEntity<Void> deleteFare(@PathVariable Integer flightNumber, @PathVariable String fareCode) {
                FarePK id = FarePK.builder()
                                .flightNumber(flightNumber)
                                .fareCode(fareCode)
                                .build();
                if (fareService.findById(id).isPresent()) {
                        fareService.deleteById(id);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
        }
}
