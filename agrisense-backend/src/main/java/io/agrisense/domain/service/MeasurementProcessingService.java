package io.agrisense.domain.service;

import java.util.List;

import io.agrisense.domain.model.Alert;
import io.agrisense.domain.model.AlertRule;
import io.agrisense.domain.model.Measurement;
import io.agrisense.domain.model.Sensor;
import io.agrisense.ports.in.ProcessMeasurementUseCase;
import io.agrisense.ports.out.AlertRepository;
import io.agrisense.ports.out.AlertRuleRepository;
import io.agrisense.ports.out.MeasurementRepository;
import io.agrisense.ports.out.SensorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class MeasurementProcessingService implements ProcessMeasurementUseCase {
    
    private final SensorRepository sensorRepository;
    private final MeasurementRepository measurementRepository;
    private final AlertRuleRepository alertRuleRepository;
    private final AlertRepository alertRepository;

    public MeasurementProcessingService(SensorRepository sensorRepository,
                                      MeasurementRepository measurementRepository,
                                      AlertRuleRepository alertRuleRepository,
                                      AlertRepository alertRepository) {
        this.sensorRepository = sensorRepository;
        this.measurementRepository = measurementRepository;
        this.alertRuleRepository = alertRuleRepository;
        this.alertRepository = alertRepository;
    }

    @Override
    @Transactional
    public void processMeasurement(Long sensorId, double value, String unit) {

        Sensor sensor = sensorRepository.findById(sensorId);
        if (sensor == null) {
            throw new IllegalArgumentException("Sensor not found: " + sensorId);
        }

        Measurement measurement = new Measurement(sensorId, value, unit);
        measurementRepository.save(measurement);

        List<AlertRule> activeRules = alertRuleRepository.findActiveBySensorId(sensorId);

         for (AlertRule rule : activeRules) {
            if (rule.isViolated(value)) {
                Alert existingOpenAlert = alertRepository.findOpenAlert(sensorId, rule.getId());
                
                if (existingOpenAlert == null) {
                    String alertMessage = String.format(
                        "Rule '%s' violated: sensor value %.2f %s", 
                        rule.getRuleName(), value, unit
                    );
                    Alert newAlert = new Alert(sensorId, rule.getId(), alertMessage);
                    alertRepository.save(newAlert);
                }
            }
        }
    }
}