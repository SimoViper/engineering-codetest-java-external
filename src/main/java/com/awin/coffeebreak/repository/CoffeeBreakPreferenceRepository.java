package com.awin.coffeebreak.repository;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CoffeeBreakPreferenceRepository extends CrudRepository<CoffeeBreakPreference, Integer> {

    @Query("select p from CoffeeBreakPreference p " +
          "where p.requestedDate > :start " +
          "and p.requestedDate < :end")
    public List<CoffeeBreakPreference> getPreferencesForToday(
          @Param("start") Instant start,
          @Param("end") Instant end
    );

}
