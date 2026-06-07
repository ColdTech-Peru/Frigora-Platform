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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports") //
public class ReportController {

    private final ReportCommandService reportCommandService;
    private final ReportQueryService reportQueryService;

    public ReportController(ReportCommandService reportCommandService, ReportQueryService reportQueryService) {
        this.reportCommandService = reportCommandService;
        this.reportQueryService = reportQueryService;
    }

    @PostMapping
    public ResponseEntity<ReportResource> createReport(@RequestBody CreateReportResource resource) {
        var command = CreateReportCommandFromResourceAssembler.toCommandFromResource(resource);
        var report = reportCommandService.handle(command);
        if (report.isEmpty()) return ResponseEntity.badRequest().build();

        var reportResource = ReportResourceFromEntityAssembler.toResourceFromEntity(report.get());
        return new ResponseEntity<>(reportResource, HttpStatus.CREATED);
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<ReportResource> getReportById(@PathVariable Integer reportId) {
        var query = new GetReportByIdQuery(reportId);
        var report = reportQueryService.handle(query);
        if (report.isEmpty()) return ResponseEntity.notFound().build();

        var reportResource = ReportResourceFromEntityAssembler.toResourceFromEntity(report.get());
        return ResponseEntity.ok(reportResource);
    }

    @GetMapping
    public ResponseEntity<List<ReportResource>> getAllReports(
            @RequestParam(required = false) Integer tenantId,
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
    public ResponseEntity<ReportResource> updateReport(@PathVariable Integer reportId, @RequestBody UpdateReportResource resource) {
        var command = UpdateReportCommandFromResourceAssembler.toCommandFromResource(reportId, resource);
        var report = reportCommandService.handle(command);
        if (report.isEmpty()) return ResponseEntity.badRequest().build();

        var reportResource = ReportResourceFromEntityAssembler.toResourceFromEntity(report.get());
        return ResponseEntity.ok(reportResource);
    }

    @DeleteMapping("/{reportId}")
    public ResponseEntity<?> deleteReport(@PathVariable Integer reportId) {
        var command = new DeleteReportCommand(reportId);
        reportCommandService.handle(command);
        return ResponseEntity.ok().build();
    }
}