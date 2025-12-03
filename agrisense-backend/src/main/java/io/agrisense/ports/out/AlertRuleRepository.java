package io.agrisense.ports.out;

import java.util.List;

import io.agrisense.domain.model.AlertRule;

public interface AlertRuleRepository {
    AlertRule save(AlertRule alertRule);
    List<AlertRule> findActiveBySensorId(Long sensorId);
}