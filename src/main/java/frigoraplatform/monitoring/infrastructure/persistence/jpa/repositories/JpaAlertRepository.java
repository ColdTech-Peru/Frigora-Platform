package frigoraplatform.monitoring.infrastructure.persistence.jpa.repositories;

import frigoraplatform.monitoring.domain.model.aggregates.Alert;
import frigoraplatform.monitoring.domain.repositories.AlertRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaAlertRepository extends JpaRepository<Alert, Long>, AlertRepository {
    @Override
    List<Alert> findByEquipmentId(Long equipmentId);

    @Override
    List<Alert> findByTenantId(Long tenantId);
}
