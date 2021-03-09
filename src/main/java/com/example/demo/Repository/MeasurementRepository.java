package com.example.demo.Repository;

import com.example.demo.Model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MeasurementRepository extends CrudRepository<Measurement, Integer>
{
    @Query("SELECT m FROM Measurement m WHERE m.Id = ?1")
    public Measurement getMeasurement(int id);
}
