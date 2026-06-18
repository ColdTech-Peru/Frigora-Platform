package frigoraplatform.monitoring.interfaces.rest;

import frigoraplatform.monitoring.domain.model.commands.DeleteEquipmentCommand;
import frigoraplatform.monitoring.domain.model.queries.GetAllEquipmentQuery;
import frigoraplatform.monitoring.domain.model.queries.GetEquipmentByIdQuery;
import frigoraplatform.monitoring.domain.services.EquipmentCommandService;
import frigoraplatform.monitoring.domain.services.EquipmentQueryService;
import frigoraplatform.monitoring.interfaces.rest.resources.CreateEquipmentResource;
import frigoraplatform.monitoring.interfaces.rest.resources.EquipmentResource;
import frigoraplatform.monitoring.interfaces.rest.transform.CreateEquipmentCommandFromResourceAssembler;
import frigoraplatform.monitoring.interfaces.rest.transform.EquipmentResourceFromEntityAssembler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/equipment")
@Tag(name = "Equipment", description = "Equipment management endpoints")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentCommandService equipmentCommandService;
    private final EquipmentQueryService equipmentQueryService;
    private final EquipmentResourceFromEntityAssembler equipmentResourceFromEntityAssembler;
    private final CreateEquipmentCommandFromResourceAssembler createEquipmentCommandFromResourceAssembler;

    @GetMapping
    @Operation(summary = "Get all equipments", description = "Returns all equipment records registered in the system.")
    public ResponseEntity<List<EquipmentResource>> getAll() {
        var equipments = equipmentQueryService.getAll(new GetAllEquipmentQuery());
        return ResponseEntity.ok(equipmentResourceFromEntityAssembler.toResources(equipments));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Gets a Equipment by Id", description = "Returns a specific equipment record by its id.")
    public ResponseEntity<EquipmentResource> getById(@PathVariable Long id) {
        return equipmentQueryService.getById(new GetEquipmentByIdQuery(id))
                .map(equipment -> ResponseEntity.ok(equipmentResourceFromEntityAssembler.toResource(equipment)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new Equipment", description = "Creates a new equipment record in the monitoring bounded context.")
    public ResponseEntity<EquipmentResource> create(@Valid @RequestBody CreateEquipmentResource resource) {
        var created = equipmentCommandService.create(
                createEquipmentCommandFromResourceAssembler.toCommand(resource));
        var response = equipmentResourceFromEntityAssembler.toResource(created);
        return ResponseEntity.created(URI.create("/api/v1/equipment/" + response.getId())).body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Equipment", description = "Deletes an equipment record by its id.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var deleted = equipmentCommandService.delete(new DeleteEquipmentCommand(id));
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
