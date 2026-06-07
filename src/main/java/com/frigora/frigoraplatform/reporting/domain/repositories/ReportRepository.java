package com.frigora.frigoraplatform.reporting.domain.repositories;


import com.frigora.frigoraplatform.reporting.domain.model.aggregates.Report;

import java.util.List;
import java.util.Optional;

public interface ReportRepository {
    // Métodos de escritura
    Report save(Report report);
    void delete(Report report);

    // Métodos de lectura
    Optional<Report> findById(Integer id);
    List<Report> findAll();
    List<Report> findAllByTenantId(Integer tenantId);
    List<Report> findAllByEquipmentId(Integer equipmentId);

    boolean existsById(Integer id);
}