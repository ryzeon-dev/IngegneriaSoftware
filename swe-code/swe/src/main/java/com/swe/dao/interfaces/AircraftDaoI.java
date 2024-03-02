package com.swe.dao.interfaces;
import java.util.Optional;

import com.swe.model.Aircraft;
public interface AircraftDaoI {
  Optional<Aircraft> findById(long id);

}
