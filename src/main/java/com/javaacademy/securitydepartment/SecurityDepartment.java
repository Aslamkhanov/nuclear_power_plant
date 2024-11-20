package com.javaacademy.securitydepartment;

import com.javaacademy.NuclearStation;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Отдел безопасности
 */
@Component
public class SecurityDepartment {
    private int incidentsForAllTime;
    private int accidentCountPeriod;
    private final NuclearStation nuclearStation;

    public SecurityDepartment(@Lazy NuclearStation nuclearStation) {
        this.nuclearStation = nuclearStation;
    }

    public void addAccident() {
        accidentCountPeriod ++;
    }
    public int getCountAccidents() {
        return accidentCountPeriod;
    }
    public void reset() {
        incidentsForAllTime += accidentCountPeriod;
        accidentCountPeriod = 0;
    }
}
