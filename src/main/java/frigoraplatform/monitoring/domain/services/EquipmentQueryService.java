package frigoraplatform.monitoring.domain.services;

import frigoraplatform.monitoring.domain.model.aggregates.Equipment;
import frigoraplatform.monitoring.domain.model.queries.GetAllEquipmentQuery;
import frigoraplatform.monitoring.domain.model.queries.GetEquipmentByIdQuery;

import java.util.List;
import java.util.Optional;

public interface EquipmentQueryService {
    List<Equipment> getAll(GetAllEquipmentQuery query);

    Optional<Equipment> getById(GetEquipmentByIdQuery query);
}
