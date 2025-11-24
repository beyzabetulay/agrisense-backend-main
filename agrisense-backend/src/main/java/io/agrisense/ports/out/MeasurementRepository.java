package io.agrisense.ports.out;

import io.agrisense.domain.model.Measurement;

public interface MeasurementRepository {
    Measurement save(Measurement measurement);
}