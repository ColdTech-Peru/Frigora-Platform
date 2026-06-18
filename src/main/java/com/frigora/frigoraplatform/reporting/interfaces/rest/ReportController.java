package com.frigora.frigoraplatform.reporting.interfaces.rest;

import com.frigora.frigoraplatform.reporting.domain.model.commands.DeleteReportCommand;
import com.frigora.frigoraplatform.reporting.domain.model.queries.GetAllReportsByEquipmentIdQuery;
import com.frigora.frigoraplatform.reporting.domain.model.queries.GetAllReportsByTenantIdQuery;
import com.frigora.frigoraplatform.reporting.domain.model.queries.GetAllReportsQuery;
import com.frigora.frigoraplatform.reporting.domain.model.queries.GetReportByIdQuery;
import com.frigora.frigoraplatform.reporting.domain.services.ReportCommandService;
import com.frigora.frigoraplatform.reporting.domain.services.ReportQueryService;
import com.frigora.frigoraplatform.reporting.interfaces.rest.resources.CreateReportResource;
import com.frigora.frigoraplatform.reporting.interfaces.rest.resources.ReportResource;
import com.frigora.frigoraplatform.reporting.interfaces.rest.resources.UpdateReportResource;
import com.frigora.frigoraplatform.reporting.interfaces.rest.transform.CreateReportCommandFromResourceAssembler;
import com.frigora.frigoraplatform.reporting.interfaces.rest.transform.ReportResourceFromEntityAssembler;
import com.frigora.frigoraplatform.reporting.interfaces.rest.transform.UpdateReportCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@Tag(name = "Reporting", description = "Available reporting endpoints / Endpoints para la gestión de reportes")
public class ReportController {

    private final ReportCommandService reportCommandService;
    private final ReportQueryService reportQueryService;

    public ReportController(ReportCommandService reportCommandService, ReportQueryService reportQueryService) {
        this.reportCommandService = reportCommandService;
        this.reportQueryService = reportQueryService;
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo reporte", description = "Registra un nuevo reporte en la base de datos.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reporte creado exitosamente."),
            @ApiResponse(responseCode = "400", description = "Error de validación o datos incompletos en la solicitud.")
    })
    public ResponseEntity<ReportResource> createReport(@RequestBody CreateReportResource resource) {
        var command = CreateReportCommandFromResourceAssembler.toCommandFromResource(resource);
        var report = reportCommandService.handle(command);
        if (report.isEmpty()) return ResponseEntity.badRequest().build();

        var reportResource = ReportResourceFromEntityAssembler.toResourceFromEntity(report.get());
        return new ResponseEntity<>(reportResource, HttpStatus.CREATED);
    }

    @GetMapping("/{reportId}")
    @Operation(summary = "Obtener un reporte por ID", description = "Devuelve los detalles de un reporte específico buscándolo por su ID único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reporte encontrado y devuelto con éxito."),
            @ApiResponse(responseCode = "404", description = "No se encontró ningún reporte con el ID proporcionado.")
    })
    public ResponseEntity<ReportResource> getReportById(
            @Parameter(description = "Identificador único del reporte", required = true)
            @PathVariable Integer reportId) {
        var query = new GetReportByIdQuery(reportId);
        var report = reportQueryService.handle(query);
        if (report.isEmpty()) return ResponseEntity.notFound().build();

        var reportResource = ReportResourceFromEntityAssembler.toResourceFromEntity(report.get());
        return ResponseEntity.ok(reportResource);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los reportes", description = "Devuelve una lista completa de reportes. Permite filtrar opcionalmente por Tenant ID o Equipment ID.")
    @ApiResponse(responseCode = "200", description = "Lista de reportes recuperada exitosamente.")
    public ResponseEntity<List<ReportResource>> getAllReports(
            @Parameter(description = "Filtro opcional para obtener reportes de un inquilino (tenant) específico")
            @RequestParam(required = false) Integer tenantId,

            @Parameter(description = "Filtro opcional para obtener reportes de un equipo de refrigeración específico")
            @RequestParam(required = false) Integer equipmentId) {

        List<com.frigora.frigoraplatform.reporting.domain.model.aggregates.Report> reports;

        if (tenantId != null) {
            reports = reportQueryService.handle(new GetAllReportsByTenantIdQuery(tenantId));
        } else if (equipmentId != null) {
            reports = reportQueryService.handle(new GetAllReportsByEquipmentIdQuery(equipmentId));
        } else {
            reports = reportQueryService.handle(new GetAllReportsQuery());
        }

        var reportResources = reports.stream()
                .map(ReportResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(reportResources);
    }

    @PutMapping("/{reportId}")
    @Operation(summary = "Actualizar un reporte", description = "Actualiza la información de un reporte existente mediante su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reporte actualizado exitosamente."),
            @ApiResponse(responseCode = "400", description = "Error al actualizar, datos inválidos o reporte no encontrado.")
    })
    public ResponseEntity<ReportResource> updateReport(
            @Parameter(description = "Identificador único del reporte a actualizar", required = true)
            @PathVariable Integer reportId,
            @RequestBody UpdateReportResource resource) {
        var command = UpdateReportCommandFromResourceAssembler.toCommandFromResource(reportId, resource);
        var report = reportCommandService.handle(command);
        if (report.isEmpty()) return ResponseEntity.badRequest().build();

        var reportResource = ReportResourceFromEntityAssembler.toResourceFromEntity(report.get());
        return ResponseEntity.ok(reportResource);
    }

    @DeleteMapping("/{reportId}")
    @Operation(summary = "Eliminar un reporte", description = "Elimina permanentemente un reporte del sistema utilizando su ID.")
    @ApiResponse(responseCode = "200", description = "Reporte eliminado exitosamente.")
    public ResponseEntity<?> deleteReport(
            @Parameter(description = "Identificador único del reporte a eliminar", required = true)
            @PathVariable Integer reportId) {
        var command = new DeleteReportCommand(reportId);
        reportCommandService.handle(command);
        return ResponseEntity.ok().build();
    }
}