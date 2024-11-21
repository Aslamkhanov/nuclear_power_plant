package com.javaacademy.nuclearstation.reactordepartment;

import com.javaacademy.nuclearstation.exceptions.NuclearFuelIsEmptyException;
import com.javaacademy.nuclearstation.exceptions.ReactorWorkException;
import com.javaacademy.nuclearstation.securitydepartment.SecurityDepartment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Component
public class ReactorDepartment {
    private final static BigDecimal KILOWATT_HOURS = BigDecimal.valueOf(10_000_000);
    private final SecurityDepartment securityDepartment;
    private int count = 0;
    private boolean isWorks;

    public BigDecimal run() throws ReactorWorkException, NuclearFuelIsEmptyException {
        if (isWorks) {
            securityDepartment.addAccident();
            throw new ReactorWorkException("Реактор уже работает");
        }
        count++;
        if (count % 100 == 0) {
            securityDepartment.addAccident();
            throw new NuclearFuelIsEmptyException();
        }
        isWorks = true;
        return KILOWATT_HOURS;
    }

    public void stop() throws ReactorWorkException {
        if (!isWorks) {
            securityDepartment.addAccident();
            throw new ReactorWorkException("Реактор уже выключен");
        }
        isWorks = false;
    }
}
