package frigoraplatform.monitoring.interfaces.rest.transform;

import frigoraplatform.monitoring.domain.model.commands.CreateEquipmentCommand;
import frigoraplatform.monitoring.interfaces.rest.resources.CreateEquipmentResource;
import org.springframework.stereotype.Component;

@Component
public class CreateEquipmentCommandFromResourceAssembler {

    public CreateEquipmentCommand toCommand(CreateEquipmentResource resource) {
        return new CreateEquipmentCommand(
                resource.getModel(),
                resource.getType(),
                resource.getSerial(),
                resource.getStatus(),
                resource.getInstalled(),
                resource.getLastSeen(),
                resource.getSetPoint(),
                resource.getName(),
                resource.getManufacturer(),
                resource.getOnline()
        );
    }
}
