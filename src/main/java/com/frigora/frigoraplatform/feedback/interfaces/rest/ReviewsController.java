package com.frigora.frigoraplatform.feedback.interfaces.rest;

import com.frigora.frigoraplatform.feedback.domain.model.queries.GetAllReviewsQuery;
import com.frigora.frigoraplatform.feedback.domain.model.queries.GetReviewByIdQuery;
import com.frigora.frigoraplatform.feedback.domain.model.queries.GetReviewsByServiceRequestIdQuery;
import com.frigora.frigoraplatform.feedback.domain.services.ReviewCommandService;
import com.frigora.frigoraplatform.feedback.domain.services.ReviewQueryService;
import com.frigora.frigoraplatform.feedback.interfaces.rest.resources.CreateReviewResource;
import com.frigora.frigoraplatform.feedback.interfaces.rest.resources.ReviewResource;
import com.frigora.frigoraplatform.feedback.interfaces.rest.transform.CreateReviewCommandFromResourceAssembler;
import com.frigora.frigoraplatform.feedback.interfaces.rest.transform.ReviewResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/reviews")
@Tag(name = "Reviews", description = "Reviews Endpoints")
public class ReviewsController {

    private final ReviewCommandService reviewCommandService;
    private final ReviewQueryService reviewQueryService;

    public ReviewsController(
            ReviewCommandService reviewCommandService,
            ReviewQueryService reviewQueryService) {
        this.reviewCommandService = reviewCommandService;
        this.reviewQueryService = reviewQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a Review")
    @ApiResponse(responseCode = "201", description = "The review was created")
    public ResponseEntity<ReviewResource> createReview(
            @RequestBody CreateReviewResource resource) {

        var command =
                CreateReviewCommandFromResourceAssembler
                        .toCommandFromResource(resource);

        var review = reviewCommandService.handle(command);

        if (review.isEmpty())
            return ResponseEntity.badRequest().build();

        var reviewResource =
                ReviewResourceFromEntityAssembler
                        .toResourceFromEntity(review.get());

        return ResponseEntity.created(
                        URI.create("/api/v1/reviews/" + reviewResource.id()))
                .body(reviewResource);
    }

    @GetMapping("/{reviewId}")
    @Operation(summary = "Get Review by Id")
    @ApiResponse(responseCode = "200", description = "The review was found")
    @ApiResponse(responseCode = "404", description = "The review was not found")
    public ResponseEntity<ReviewResource> getReviewById(
            @PathVariable int reviewId) {

        var query = new GetReviewByIdQuery(reviewId);

        var review = reviewQueryService.handle(query);

        if (review.isEmpty())
            return ResponseEntity.notFound().build();

        var reviewResource =
                ReviewResourceFromEntityAssembler
                        .toResourceFromEntity(review.get());

        return ResponseEntity.ok(reviewResource);
    }

    @GetMapping("/service-request/{serviceRequestId}")
    @Operation(summary = "Get Reviews by Service Request Id")
    @ApiResponse(responseCode = "200", description = "The reviews were found")
    public ResponseEntity<?> getReviewsByServiceRequestId(
            @PathVariable int serviceRequestId) {

        var query =
                new GetReviewsByServiceRequestIdQuery(serviceRequestId);

        var reviews = reviewQueryService.handle(query);

        var resources = reviews.stream()
                .map(ReviewResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping
    @Operation(summary = "Get All Reviews")
    @ApiResponse(responseCode = "200", description = "The reviews were found")
    public ResponseEntity<?> getAllReviews() {

        var reviews =
                reviewQueryService.handle(new GetAllReviewsQuery());

        var resources = reviews.stream()
                .map(ReviewResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}