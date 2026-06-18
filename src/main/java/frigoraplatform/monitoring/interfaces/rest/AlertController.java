package frigoraplatform.monitoring.interfaces.rest;

import frigoraplatform.monitoring.domain.model.commands.AcknowledgeAlertCommand;
import frigoraplatform.monitoring.domain.model.commands.DeleteAlertCommand;
import frigoraplatform.monitoring.domain.model.queries.GetAllAlertsByEquipmentIdQuery;
import frigoraplatform.monitoring.domain.model.queries.GetAllAlertsByTenantIdQuery;
import frigoraplatform.monitoring.domain.model.queries.GetAllAlertsQuery;
import frigoraplatform.monitoring.domain.model.queries.GetAlertByIdQuery;
import frigoraplatform.monitoring.domain.services.AlertCommandService;
import frigoraplatform.monitoring.domain.services.AlertQueryService;
import frigoraplatform.monitoring.interfaces.rest.resources.AlertResource;
import frigoraplatform.monitoring.interfaces.rest.resources.CreateAlertResource;
import frigoraplatform.monitoring.interfaces.rest.transform.AlertResourceFromEntityAssembler;
import frigoraplatform.monitoring.interfaces.rest.transform.CreateAlertCommandFromResourceAssembler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/alert")
@Tag(name = "Alert", description = "Alert management endpoints")
@RequiredArgsConstructor
public class AlertController {

    private final AlertCommandService alertCommandService;
    private final AlertQueryService alertQueryService;
    private final AlertResourceFromEntityAssembler alertResourceFromEntityAssembler;
    private final CreateAlertCommandFromResourceAssembler createAlertCommandFromResourceAssembler;

    @GetMapping
    @Operation(summary = "Gets all Alerts or by Tenant/Equipment", description = "Returns all alerts or filters them by tenantId or equipmentId.")
    public ResponseEntity<List<AlertResource>> getAll(
            @RequestParam(required = false) Long tenantId,
            @RequestParam(required = false) Long equipmentId) {
        var alerts = tenantId != null
                ? alertQueryService.getAllByTenantId(new GetAllAlertsByTenantIdQuery(tenantId))
                : equipmentId != null
                ? alertQueryService.getAllByEquipmentId(new GetAllAlertsByEquipmentIdQuery(equipmentId))
                : alertQueryService.getAll(new GetAllAlertsQuery());

        return ResponseEntity.ok(alertResourceFromEntityAssembler.toResources(alerts));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Gets all Alerts By Id", description = "Returns a specific alert by its id.")
    public ResponseEntity<AlertResource> getById(@PathVariable Long id) {
        return alertQueryService.getById(new GetAlertByIdQuery(id))
                .map(alert -> ResponseEntity.ok(alertResourceFromEntityAssembler.toResource(alert)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new Alert", description = "Creates a new alert in the monitoring bounded context.")
    public ResponseEntity<AlertResource> create(@Valid @RequestBody CreateAlertResource resource) {
        var created = alertCommandService.create(createAlertCommandFromResourceAssembler.toCommand(resource));
        var response = alertResourceFromEntityAssembler.toResource(created);
        return ResponseEntity.created(URI.create("/api/v1/alert/" + response.getId())).body(response);
    }

    @PatchMapping("/{id}/acknowledge")
    @Operation(summary = "Acknowledges an Alert", description = "Changes the alert status to Acknowledged.")
    public ResponseEntity<AlertResource> acknowledge(@PathVariable Long id) {
        return alertCommandService.acknowledge(new AcknowledgeAlertCommand(id))
                .map(alert -> ResponseEntity.ok(alertResourceFromEntityAssembler.toResource(alert)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Alert", description = "Deletes an alert by its id.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var deleted = alertCommandService.delete(new DeleteAlertCommand(id));
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
