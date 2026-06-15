package com.frigora.frigoraplatform.servicerequests.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Embeddable
@Getter
public class ServiceRequestPriority {

    public enum EServiceRequestPriority {
        HIGH,
        MEDIUM,
        LOW
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private EServiceRequestPriority priority = EServiceRequestPriority.MEDIUM;

    public ServiceRequestPriority() {
        this.priority = EServiceRequestPriority.MEDIUM;
    }

    public ServiceRequestPriority(EServiceRequestPriority priority) {
        this.priority = priority;
    }
}