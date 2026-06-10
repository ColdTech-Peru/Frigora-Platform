package frigoraplatform.monitoring.interfaces.rest.resources;

import frigoraplatform.monitoring.domain.model.valueobjects.EAlertSeverity;
import frigoraplatform.monitoring.domain.model.valueobjects.EAlertStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlertResource {
    private Long id;
    private Long tenantId;
    private Long equipmentId;
    private Long siteId;
    private String type;
    private EAlertSeverity severity;
    private String description;
    private EAlertStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdAt;
}
