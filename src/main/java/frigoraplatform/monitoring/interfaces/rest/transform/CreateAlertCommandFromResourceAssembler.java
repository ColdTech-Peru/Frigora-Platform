package frigoraplatform.monitoring.interfaces.rest.transform;

import frigoraplatform.monitoring.domain.model.commands.CreateAlertCommand;
import frigoraplatform.monitoring.interfaces.rest.resources.CreateAlertResource;
import org.springframework.stereotype.Component;

@Component
public class CreateAlertCommandFromResourceAssembler {

    public CreateAlertCommand toCommand(CreateAlertResource resource) {
        return new CreateAlertCommand(
                resource.getTenantId(),
                resource.getEquipmentId(),
                resource.getSiteId(),
                resource.getType(),
                resource.getSeverity(),
                resource.getDescription(),
                resource.getStatus()
        );
    }
}
