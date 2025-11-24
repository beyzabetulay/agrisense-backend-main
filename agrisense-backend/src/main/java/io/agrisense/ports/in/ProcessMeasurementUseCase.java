package io.agrisense.ports.in;

public interface ProcessMeasurementUseCase {
    void processMeasurement(Long sensorId, double value, String unit);
}