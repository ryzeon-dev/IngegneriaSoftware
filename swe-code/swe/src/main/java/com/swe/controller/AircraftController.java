package com.swe.controller;
import com.swe.model.Aircraft;
import com.swe.service.interfaces.AircraftServiceI;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AircraftController {
  private final AircraftServiceI aircraftService;
  @Autowired
  public AircraftController(AircraftServiceI aircraftService){
    this.aircraftService = aircraftService;

  }
	@GetMapping("/aircraft/{id}")
	public Aircraft getAircraft(@PathVariable("id") long id) {
    System.out.println("requested Aircraft: " + id );
    Optional<Aircraft> foo=aircraftService.getAircraft(id);
    return foo.get();
  }
		
}
