package io.agrisense.adapter.in.web.controller;

import io.agrisense.ports.in.ProcessMeasurementUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/api/measurements")
public class MeasurementController {

    @Inject
    ProcessMeasurementUseCase processMeasurementUseCase;

    public static class MeasurementRequest {
        public Long sensorId;
        public Double value;
        public String unit;

        public MeasurementRequest() { }

        public Long getSensorId() { return sensorId; }
        public void setSensorId(Long sensorId) { this.sensorId = sensorId; }
        public Double getValue() { return value; }
        public void setValue(Double value) { this.value = value; }
        public String getUnit() { return unit; }
        public void setUnit(String unit) { this.unit = unit; }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postMeasurement(MeasurementRequest req) {
        if (req == null || req.sensorId == null || req.value == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("sensorId and value are required").build();
        }
        // delegate to domain use-case
        processMeasurementUseCase.processMeasurement(req.sensorId, req.value, req.unit == null ? "" : req.unit);
        return Response.ok().entity("accepted").build();
    }
}
