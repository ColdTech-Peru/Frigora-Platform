package frigoraplatform.monitoring.infrastructure.persistence.jpa.repositories;

import frigoraplatform.monitoring.domain.model.aggregates.Equipment;
import frigoraplatform.monitoring.domain.repositories.EquipmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaEquipmentRepository extends JpaRepository<Equipment, Long>, EquipmentRepository {
}
