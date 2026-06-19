package com.frigora.frigoraplatform.feedback.interfaces.rest.resources;

public record CreateReviewResource(
        int serviceRequestId,
        int ownerId,
        int technicianId,
        int rating,
        String comment
) {
}
