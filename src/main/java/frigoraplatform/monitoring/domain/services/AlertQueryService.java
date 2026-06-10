package frigoraplatform.monitoring.domain.services;

import frigoraplatform.monitoring.domain.model.aggregates.Alert;
import frigoraplatform.monitoring.domain.model.queries.GetAllAlertsByEquipmentIdQuery;
import frigoraplatform.monitoring.domain.model.queries.GetAllAlertsByTenantIdQuery;
import frigoraplatform.monitoring.domain.model.queries.GetAllAlertsQuery;
import frigoraplatform.monitoring.domain.model.queries.GetAlertByIdQuery;

import java.util.List;
import java.util.Optional;

public interface AlertQueryService {
    List<Alert> getAll(GetAllAlertsQuery query);

    Optional<Alert> getById(GetAlertByIdQuery query);

    List<Alert> getAllByEquipmentId(GetAllAlertsByEquipmentIdQuery query);

    List<Alert> getAllByTenantId(GetAllAlertsByTenantIdQuery query);
}
