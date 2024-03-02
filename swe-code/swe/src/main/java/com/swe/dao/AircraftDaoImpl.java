package com.swe.dao;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.swe.dao.interfaces.AircraftDaoI;
import com.swe.model.Aircraft;

@Component
@Repository
public class AircraftDaoImpl implements AircraftDaoI {
  public AircraftDaoImpl(){
    
  }
  @Override
  public Optional<Aircraft> findById(long id) {
    // TODO Auto-generated method stub
    Optional<Aircraft> foo = Optional.of(new Aircraft(id));
    return foo;
  }
}
