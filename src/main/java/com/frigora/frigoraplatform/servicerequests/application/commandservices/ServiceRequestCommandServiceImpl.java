package com.frigora.frigoraplatform.servicerequests.application.commandservices;

import com.frigora.frigoraplatform.servicerequests.domain.model.aggregates.ServiceRequest;
import com.frigora.frigoraplatform.servicerequests.domain.model.commands.*;
import com.frigora.frigoraplatform.servicerequests.domain.services.ServiceRequestCommandService;
import com.frigora.frigoraplatform.servicerequests.infrastructure.persistence.jpa.repositories.ServiceRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceRequestCommandServiceImpl implements ServiceRequestCommandService {

    private final ServiceRequestRepository serviceRequestRepository;

    public Optional<ServiceRequest> handle(CreateServiceRequestCommand command) {
        var serviceRequest = new ServiceRequest(
                command.requesterId(),
                command.siteId(),
                command.equipmentId(),
                command.assignedTo(),
                command.origin(),
                command.type(),
                command.priority(),
                command.description()
        );
        serviceRequestRepository.save(serviceRequest);
        return Optional.of(serviceRequest);
    }

    public Optional<ServiceRequest> handle(AcceptServiceRequestCommand command) {
        return serviceRequestRepository.findById((long) command.serviceRequestId())
                .map(serviceRequest -> {
                    serviceRequest.accept();
                    serviceRequestRepository.save(serviceRequest);
                    return serviceRequest;
                });
    }

    public Optional<ServiceRequest> handle(RejectServiceRequestCommand command) {
        return serviceRequestRepository.findById((long) command.ServiceRequestId())
                .map(serviceRequest -> {
                    serviceRequest.reject();
                    serviceRequestRepository.save(serviceRequest);
                    return serviceRequest;
                });
    }

    public Optional<ServiceRequest> handle(CancelServiceRequestCommand command) {
        return serviceRequestRepository.findById((long) command.ServiceRequestId())
                .map(serviceRequest -> {
                    serviceRequest.cancel();
                    serviceRequestRepository.save(serviceRequest);
                    return serviceRequest;
                });
    }

    public Optional<ServiceRequest> handle(AssignTechnicianToServiceRequestCommand command) {
        return serviceRequestRepository.findById((long) command.serviceRequestId())
                .map(serviceRequest -> {
                    serviceRequest.assignTechnician(command.technicianId());
                    serviceRequestRepository.save(serviceRequest);
                    return serviceRequest;
                });
    }

    public Optional<ServiceRequest> handle(CompleteServiceRequestCommand command) {
        return serviceRequestRepository.findById((long) command.ServiceRequestId())
                .map(serviceRequest -> {
                    serviceRequest.complete();
                    serviceRequestRepository.save(serviceRequest);
                    return serviceRequest;
                });
    }
}