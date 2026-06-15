package com.frigora.frigoraplatform.servicerequests.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Embeddable
@Getter
public class ServiceRequestType {

    public enum EServiceRequestType {
        CORRECTIVE,
        PREVENTIVE
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private EServiceRequestType type = EServiceRequestType.CORRECTIVE;

    public ServiceRequestType() {
        this.type = EServiceRequestType.CORRECTIVE;
    }

    public ServiceRequestType(EServiceRequestType type) {
        this.type = type;
    }
}
