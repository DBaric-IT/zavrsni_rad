package com.example.demo.Service;

import com.example.demo.Model.*;
import com.example.demo.Repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeasurementService
{
    @Autowired
    MeasurementRepository measurementRepository;
    //save or update measurement
    public void saveMeasurement(Measurement measurement) {
        measurementRepository.save(measurement);
    }

    public Measurement getMeasurement(int id){
        return measurementRepository.getMeasurement(id);
    }

    public void deleteMeasurement(Measurement measurement){
        measurementRepository.delete(measurement);
    }
}