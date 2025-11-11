package com.dyma.tennis.web;

import com.dyma.tennis.service.HealthCheckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "HealthCheck API")
@RestController
public class HealthCheckController {

    @Autowired
    private HealthCheckService healthCheckService;


    @Operation(summary = "Vérifie l'état de santé de l'application", description = "Retourne le statut actuel de l'application pour indiquer si elle est opérationnelle.")
    @ApiResponse(responseCode = "200", description = "L'application est démarrée et fonctionne correctement")
    @GetMapping("/health-check")
    public HealthCheck healthCheck() {
        return healthCheckService.gethealthCheck();
    }

}
