package com.dyma.tennis.service;

import com.dyma.tennis.repository.HealthCheckRepository;
import com.dyma.tennis.web.ApplicationStatus;
import com.dyma.tennis.web.HealthCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckService {

    @Autowired
    private HealthCheckRepository healthCheckRepository;


    public HealthCheck gethealthCheck() {
        return new HealthCheck(ApplicationStatus.Ok, "Application is up and running.");

    }

    public HealthCheck healthCheck() {
        Long activateSession = HealthCheckRepository.countApplicationConnections();
        if (activateSession > 0) {
            return new HealthCheck(ApplicationStatus.Ok, "Welcome to Dyma Tennis");
        } else {
            return new HealthCheck(ApplicationStatus.KO, "Dyma Tennis is not fully functional");


        }
    }


}
