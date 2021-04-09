package br.com.vacinas.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vacinas.api.exception.BadRequestException;
import br.com.vacinas.api.model.Vaccine;
import br.com.vacinas.api.repository.UserRepostory;
import br.com.vacinas.api.repository.VaccineRepository;

@RestController
@RequestMapping("/api")
public class VaccineController {
    @Autowired
    VaccineRepository vaccineRepository;

    @Autowired
    UserRepostory userRepostory;

    @GetMapping("/vaccines")
    public List<Vaccine> getAllVaccines() {
        return vaccineRepository.findAll();
    }
    
    
    @PostMapping("/vaccines")
    public Vaccine  createVaccine(@Valid @RequestBody Vaccine vaccine) {
        long userId = vaccine.getUser().getId();

        return userRepostory.findById(userId).map(user -> {
            vaccine.setUser(user);
            return vaccineRepository.save(vaccine);
        }).orElseThrow(() -> new BadRequestException());

    }

    @GetMapping("/vaccines/{id}")
    public Vaccine getVaccineById(@PathVariable(value = "id") Long vaccineId) {
        return vaccineRepository.findById(vaccineId)
            .orElseThrow(() -> new BadRequestException());
    }

    @PutMapping("/vaccines/{id}")
    public Vaccine updateVaccine(@PathVariable(value = "id") Long vaccineId, @Valid @RequestBody Vaccine vaccineDetails) {
        Vaccine vaccine = vaccineRepository.findById(vaccineId)
            .orElseThrow(() -> new BadRequestException());

        vaccine.setName(vaccineDetails.getName());
        vaccine.setUser(vaccineDetails.getUser());
        vaccine.setDate(vaccineDetails.getDate());

        long userId = vaccineDetails.getUser().getId();
        return userRepostory.findById(userId).map(user -> {
            vaccine.setUser(user);
            return vaccineRepository.save(vaccine);
        }).orElseThrow(() -> new BadRequestException());
    }

    @DeleteMapping("/vaccines/{id}")
    public ResponseEntity<?> deleteVaccine(@PathVariable(value = "id") Long vaccineId) {
        Vaccine vaccine = vaccineRepository.findById(vaccineId)
            .orElseThrow(() -> new BadRequestException());

        vaccineRepository.delete(vaccine);

        return ResponseEntity.ok().build();
    }
}

