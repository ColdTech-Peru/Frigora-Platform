package com.frigora.frigoraplatform.feedback.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class OwnerId {
    private int value;
    public OwnerId(int value) {
        this.value = value;
    }
}