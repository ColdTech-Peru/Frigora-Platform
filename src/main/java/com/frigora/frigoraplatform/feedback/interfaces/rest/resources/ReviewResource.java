package com.frigora.frigoraplatform.feedback.interfaces.rest.resources;

public record ReviewResource(
        int id,
        int serviceRequestId,
        int ownerId,
        int technicianId,
        int rating,
        String comment
) {
}
