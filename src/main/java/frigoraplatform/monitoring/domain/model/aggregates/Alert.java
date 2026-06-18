package frigoraplatform.monitoring.domain.model.aggregates;

import frigoraplatform.monitoring.domain.model.valueobjects.EAlertSeverity;
import frigoraplatform.monitoring.domain.model.valueobjects.EAlertStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tenantId;

    @Column(nullable = false)
    private Long equipmentId;

    @Column(nullable = false)
    private Long siteId;

    @Column(nullable = false)
    private String type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EAlertSeverity severity;

    @Column(nullable = false, length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EAlertStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public void acknowledge() {
        this.status = EAlertStatus.Acknowledged;
    }

    public void resolve() {
        this.status = EAlertStatus.Resolved;
    }

    public void close() {
        this.status = EAlertStatus.Closed;
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
