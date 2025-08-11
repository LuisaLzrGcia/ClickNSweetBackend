package com.clicknsweet.clicknsweet.controller;

import com.clicknsweet.clicknsweet.model.State;
import com.clicknsweet.clicknsweet.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/clicknsweet/states")
public class StateController {

    @Autowired
    private StateRepository stateRepository;

    // Obtener todos los estados
    @GetMapping
    public List<State> getAllStates() {
        return stateRepository.findAll();
    }

    // Obtener estado por id
    @GetMapping("/{id}")
    public ResponseEntity<State> getStateById(@PathVariable Integer id) {
        Optional<State> state = stateRepository.findById(id);
        return state.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener estados por país
    @GetMapping("/country/{countryId}")
    public List<State> getStatesByCountry(@PathVariable Integer countryId) {
        return stateRepository.findByCountryId(countryId);
    }

    // Crear nuevo estado
    @PostMapping
    public State createState(@RequestBody State state) {
        return stateRepository.save(state);
    }

    // Actualizar estado
    @PutMapping("/{id}")
    public ResponseEntity<State> updateState(@PathVariable Integer id, @RequestBody State stateDetails) {
        Optional<State> stateOptional = stateRepository.findById(id);
        if (!stateOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        State state = stateOptional.get();
        state.setName(stateDetails.getName());
        state.setLatitude(stateDetails.getLatitude());
        state.setLongitude(stateDetails.getLongitude());
        state.setCountry(stateDetails.getCountry());
        stateRepository.save(state);
        return ResponseEntity.ok(state);
    }

}
