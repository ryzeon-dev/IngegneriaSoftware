package com.swe.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swe.dao.interfaces.AircraftDaoI;
import com.swe.model.Aircraft;
import com.swe.service.interfaces.AircraftServiceI;
@Service
public class AircraftService implements AircraftServiceI {
  private final AircraftDaoI aircraftDao;
  @Autowired
  public AircraftService(AircraftDaoI aircraftDao){
    this.aircraftDao = aircraftDao;

  }

  public Optional<Aircraft> getAircraft(long id){
    return aircraftDao.findById(id);
  }
}
