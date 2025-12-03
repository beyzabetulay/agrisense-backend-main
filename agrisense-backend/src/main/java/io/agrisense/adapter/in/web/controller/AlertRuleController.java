package io.agrisense.adapter.in.web.controller;

import java.net.URI;
import java.util.List;

import io.agrisense.adapter.in.web.dto.CreateAlertRuleRequest;
import io.agrisense.domain.model.AlertRule;
import io.agrisense.domain.service.AlertRuleService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

// REST Controller for UC-03: Define Alert Rule
@Path("/sensors/{sensorId}/rules")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AlertRuleController {
    
    private final AlertRuleService alertRuleService;
    
    @Inject
    public AlertRuleController(AlertRuleService alertRuleService) {
        this.alertRuleService = alertRuleService;
    }
    
    @POST
    public Response createAlertRule(
            @PathParam("sensorId") Long sensorId,
            CreateAlertRuleRequest request) {
        
        try {
            AlertRule alertRule = new AlertRule();
            alertRule.setThreshold(request.getValue());
            alertRule.setCondition(request.getCondition());
            alertRule.setRuleName(request.getName());
            alertRule.setActive(true);

            AlertRule createdRule = alertRuleService.createRule(sensorId, alertRule);

            return Response
                    .created(URI.create("/sensors/" + sensorId + "/rules/" + createdRule.getId()))
                    .entity(createdRule)
                    .build();

        } catch (IllegalArgumentException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }
    
    @GET
    public Response getActiveAlertRules(@PathParam("sensorId") Long sensorId) {
        try {
            List<AlertRule> activeRules = alertRuleService.getActiveRules(sensorId);
            return Response.ok(activeRules).build();
            
        } catch (IllegalArgumentException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
                    
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Internal server error\"}")
                    .build();
        }
    }
}
