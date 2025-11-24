package io.agrisense.ports.out;

import io.agrisense.domain.model.Sensor;

public interface SensorRepository {
    Sensor findById(Long id);
    Sensor save(Sensor sensor);
}