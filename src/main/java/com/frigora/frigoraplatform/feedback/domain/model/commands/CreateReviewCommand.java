package com.frigora.frigoraplatform.feedback.domain.model.commands;

public record CreateReviewCommand(int serviceRequestId, int ownerId, int technicianId, int rating, String comment) {}
