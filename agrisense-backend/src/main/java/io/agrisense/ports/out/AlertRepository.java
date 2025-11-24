package io.agrisense.ports.out;

import io.agrisense.domain.model.Alert;

public interface AlertRepository {
    Alert save(Alert alert);
    Alert findOpenAlert(Long sensorId, Long ruleId);
}