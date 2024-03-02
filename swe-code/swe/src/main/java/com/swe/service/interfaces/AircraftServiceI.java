package com.swe.service.interfaces;
import java.util.Optional;

import com.swe.model.Aircraft;
public interface AircraftServiceI {
  public Optional<Aircraft> getAircraft(long id);
}
