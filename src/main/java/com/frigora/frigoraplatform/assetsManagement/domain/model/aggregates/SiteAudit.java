package com.frigora.frigoraplatform.assetsManagement.domain.model.aggregates;

import jakarta.persistence.Column;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class SiteAudit  {
    @CreatedDate
    @Column(name = "Created")
    private OffsetDateTime createdDate;

    @LastModifiedDate
    @Column(name = "Updated")
    private OffsetDateTime updatedDate;
}
