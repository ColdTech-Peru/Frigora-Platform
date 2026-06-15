package com.frigora.frigoraplatform.servicerequests.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Embeddable
@Getter
public class ServiceRequestStatus {

    public enum EServiceRequestStatus {
        PENDING,
        ACCEPTED,
        IN_PROGRESS,
        COMPLETED,
        CANCELED,
        REJECTED
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EServiceRequestStatus status = EServiceRequestStatus.PENDING;

    public ServiceRequestStatus() {
        this.status = EServiceRequestStatus.PENDING;
    }

    public ServiceRequestStatus(EServiceRequestStatus status) {
        this.status = status;
    }
}
