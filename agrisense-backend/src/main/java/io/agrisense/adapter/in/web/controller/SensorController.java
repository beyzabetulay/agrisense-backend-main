package io.agrisense.adapter.in.web.controller;

import java.util.List;

import io.agrisense.domain.model.ESensorType;
import io.agrisense.domain.model.Sensor;
import io.agrisense.ports.out.SensorRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/sensors")
public class SensorController {

    @Inject
    SensorRepository sensorRepository;

    public static class SensorRequest {
        public String name;
        public ESensorType type;
        public String apiKey;
        public Long fieldId;

        public SensorRequest() { }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public ESensorType getType() { return type; }
        public void setType(ESensorType type) { this.type = type; }
        public String getApiKey() { return apiKey; }
        public void setApiKey(String apiKey) { this.apiKey = apiKey; }
        public Long getFieldId() { return fieldId; }
        public void setFieldId(Long fieldId) { this.fieldId = fieldId; }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSensor(SensorRequest req) {
        if (req == null || req.name == null || req.type == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("name and type are required").build();
        }
        
        Sensor sensor = new Sensor(req.name, req.type, req.apiKey, req.fieldId);
        Sensor saved = sensorRepository.save(sensor);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSensors() {
        List<Sensor> sensors = sensorRepository.findAll();
        return Response.ok(sensors).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSensorById(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("id is required").build();
        }
        
        Sensor sensor = sensorRepository.findById(id);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("sensor not found").build();
        }
        
        return Response.ok(sensor).build();
    }
}