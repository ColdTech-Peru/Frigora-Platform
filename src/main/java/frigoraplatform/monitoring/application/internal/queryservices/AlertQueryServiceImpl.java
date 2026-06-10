package frigoraplatform.monitoring.application.internal.queryservices;

import frigoraplatform.monitoring.domain.model.aggregates.Alert;
import frigoraplatform.monitoring.domain.model.queries.GetAllAlertsByEquipmentIdQuery;
import frigoraplatform.monitoring.domain.model.queries.GetAllAlertsByTenantIdQuery;
import frigoraplatform.monitoring.domain.model.queries.GetAllAlertsQuery;
import frigoraplatform.monitoring.domain.model.queries.GetAlertByIdQuery;
import frigoraplatform.monitoring.domain.repositories.AlertRepository;
import frigoraplatform.monitoring.domain.services.AlertQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlertQueryServiceImpl implements AlertQueryService {

    private final AlertRepository alertRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Alert> getAll(GetAllAlertsQuery query) {
        return alertRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Alert> getById(GetAlertByIdQuery query) {
        return alertRepository.findById(query.id());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alert> getAllByEquipmentId(GetAllAlertsByEquipmentIdQuery query) {
        return alertRepository.findByEquipmentId(query.equipmentId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alert> getAllByTenantId(GetAllAlertsByTenantIdQuery query) {
        return alertRepository.findByTenantId(query.tenantId());
    }
}
