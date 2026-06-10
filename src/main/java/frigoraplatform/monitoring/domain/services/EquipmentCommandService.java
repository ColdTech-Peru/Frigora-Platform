package frigoraplatform.monitoring.domain.services;

import frigoraplatform.monitoring.domain.model.aggregates.Equipment;
import frigoraplatform.monitoring.domain.model.commands.CreateEquipmentCommand;
import frigoraplatform.monitoring.domain.model.commands.DeleteEquipmentCommand;

public interface EquipmentCommandService {
    Equipment create(CreateEquipmentCommand command);

    boolean delete(DeleteEquipmentCommand command);
}
