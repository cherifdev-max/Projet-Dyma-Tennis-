package com.dyma.tennis.repository;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HealthCheckRepository {

    @Autowired
    private static EntityManager entityManager;



        public static Long countApplicationConnections() {
            String applicationConnectionsQuery = "select count(*) from pg_stat_activity where application_name = 'PostgreSQL JDBC Driver'";
            return (Long) entityManager.createNativeQuery(applicationConnectionsQuery).getSingleResult();
        }

}
