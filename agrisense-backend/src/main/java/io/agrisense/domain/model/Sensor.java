package io.agrisense.domain.model;

public class Sensor {
    private Long id;
    private String name;
    private ESensorType type;

    public Sensor() {
    }

    public Sensor(String name, ESensorType type) {
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ESensorType getType() {
        return type;
    }

    public void setType(ESensorType type) {
        this.type = type;
    }
}
