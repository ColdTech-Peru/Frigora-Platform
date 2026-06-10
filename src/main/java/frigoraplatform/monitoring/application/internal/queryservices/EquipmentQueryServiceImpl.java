package frigoraplatform.monitoring.application.internal.queryservices;

import frigoraplatform.monitoring.domain.model.aggregates.Equipment;
import frigoraplatform.monitoring.domain.model.queries.GetAllEquipmentQuery;
import frigoraplatform.monitoring.domain.model.queries.GetEquipmentByIdQuery;
import frigoraplatform.monitoring.domain.repositories.EquipmentRepository;
import frigoraplatform.monitoring.domain.services.EquipmentQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipmentQueryServiceImpl implements EquipmentQueryService {

    private final EquipmentRepository equipmentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Equipment> getAll(GetAllEquipmentQuery query) {
        return equipmentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Equipment> getById(GetEquipmentByIdQuery query) {
        return equipmentRepository.findById(query.id());
    }
}
