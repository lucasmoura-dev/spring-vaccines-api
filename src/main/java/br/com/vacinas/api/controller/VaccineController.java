package br.com.vacinas.api.controller;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.vacinas.api.exception.ResourceNotFoundException;
import br.com.vacinas.api.model.Vaccine;
import br.com.vacinas.api.repository.UserRepostory;
import br.com.vacinas.api.repository.VaccineRepository;

@RestController
@RequestMapping("/api")
public class VaccineController {
    @Autowired
    VaccineRepository vaccineRepository;
    UserRepostory userRepostory;

    @GetMapping("/vaccines")
    public List<Vaccine> getAllVaccines() {
        return vaccineRepository.findAll();
    }
    
    
    @PostMapping("/vaccines")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity  createVaccine(@RequestBody Vaccine vaccine) {
        if (vaccine.getUser() != null) {
            return new ResponseEntity("User not found", HttpStatus.BAD_REQUEST);
            //throw new ResourceNotFoundException("User", "id", vaccine.getUser().getId());
        }
        
        vaccineRepository.save(vaccine);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/vaccines/{id}")
    public Vaccine getVaccineById(@PathVariable(value = "id") Long vaccineId) {
        return vaccineRepository.findById(vaccineId)
            .orElseThrow(() -> new ResourceNotFoundException("Vaccine", "id", vaccineId));
    }

    @PutMapping("/vaccines/{id}")
    public Vaccine updateUser(@PathVariable(value = "id") Long vaccineId, @RequestBody Vaccine vaccineDetails) {
        Vaccine vaccine = vaccineRepository.findById(vaccineId)
            .orElseThrow(() -> new ResourceNotFoundException("Vaccine", "id", vaccineId));


        vaccine.setName(vaccineDetails.getName());
        vaccine.setUser(vaccineDetails.getUser());
        vaccine.setDate(vaccineDetails.getDate());

        Vaccine updatedVaccine = vaccineRepository.save(vaccine);
        return updatedVaccine;
    }

    @DeleteMapping("/vaccines/{id}")
    public ResponseEntity<?> deleteVaccine(@PathVariable(value = "id") Long vaccineId) {
        Vaccine vaccine = vaccineRepository.findById(vaccineId)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", vaccineId));

        vaccineRepository.delete(vaccine);

        return ResponseEntity.ok().build();
    }
}

