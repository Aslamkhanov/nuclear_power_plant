package com.javaacademy.nuclear_station.department;

import com.javaacademy.nuclear_station.exceptions.NuclearFuelIsEmptyException;
import com.javaacademy.nuclear_station.exceptions.ReactorWorkException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Component
public class ReactorDepartment {
    private final BigDecimal kilowattHOURS = BigDecimal.valueOf(10_000_000);
    private final SecurityDepartment securityDepartment;
    private final int hundred = 100;
    private int count = 0;
    private boolean isWorks;

    public BigDecimal run() throws ReactorWorkException, NuclearFuelIsEmptyException {
        if (isWorks) {
            securityDepartment.addAccident();
            throw new ReactorWorkException("Реактор уже работает");
        }
        count++;
        if (count % hundred == 0) {
            securityDepartment.addAccident();
            throw new NuclearFuelIsEmptyException();
        }
        isWorks = true;
        return kilowattHOURS;
    }

    public void stop() throws ReactorWorkException {
        if (!isWorks) {
            securityDepartment.addAccident();
            throw new ReactorWorkException("Реактор уже выключен");
        }
        isWorks = false;
    }
}
