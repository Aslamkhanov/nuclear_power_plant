package com.javaacademy;

import com.javaacademy.exceptions.NuclearFuelIsEmptyException;
import com.javaacademy.exceptions.ReactorWorkException;
import com.javaacademy.reactordepartment.ReactorDepartment;
import com.javaacademy.securitydepartment.SecurityDepartment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
@RequiredArgsConstructor
public class NuclearStation {
    private final SecurityDepartment securityDepartment;
    private final ReactorDepartment reactorDepartment;
    @Getter
    private BigDecimal totalAmountOfEnergyGenerated = BigDecimal.ZERO;
    @Getter
    private int accidentCountAllTime;

    public void start(int year) {
        for (int i = 0; i < year; i++) {
            startYear();
        }
        log.info("Количество инцидентов за всю работу станции: {} ", accidentCountAllTime);
    }
    public void startYear() {
        BigDecimal kilowattHoursOfYear = BigDecimal.ZERO;
        log.info("Атомная станция начала работу");
        for (int i = 0; i < 365; i++) {
            try {
                BigDecimal kilowattHoursOfDay = reactorDepartment.run();
                kilowattHoursOfYear = kilowattHoursOfYear.add(kilowattHoursOfDay);
                reactorDepartment.stop();
            } catch (NuclearFuelIsEmptyException e) {
                log.info("Происходят работы на атомной станции! Электричества нет!");
            } catch (ReactorWorkException e) {
                log.info(e.getMessage());
            }
        }
        log.info("Атомная станция закончила работу.");
        log.info("За год Выработано: {} киловатт/часов", kilowattHoursOfYear);
        log.info("Количество инцидентов за год: {} ",securityDepartment.getCountAccidents());
        securityDepartment.reset();
    }

    public void incrementAccident(int count) {
        accidentCountAllTime += count;
    }
}
