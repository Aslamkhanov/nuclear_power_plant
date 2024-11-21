package com.javaacademy.nuclearstation.securitydepartment;

import com.javaacademy.nuclearstation.NuclearStation;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class SecurityDepartment {
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
        nuclearStation.incrementAccident(accidentCountPeriod);
        accidentCountPeriod = 0;
    }
}
