package com.javaacademy.nuclear_station;

import com.javaacademy.nuclear_station.department.ReactorDepartment;
import com.javaacademy.nuclear_station.department.SecurityDepartment;
import com.javaacademy.nuclear_station.department.economic_department.EconomicDepartment;
import com.javaacademy.nuclear_station.exceptions.NuclearFuelIsEmptyException;
import com.javaacademy.nuclear_station.exceptions.ReactorWorkException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@Slf4j
@RequiredArgsConstructor
public class NuclearStation {
    private final SecurityDepartment securityDepartment;
    private final ReactorDepartment reactorDepartment;
    private final EconomicDepartment economicDepartment;
    @Getter
    private BigDecimal totalAmountOfEnergyGenerated = BigDecimal.ZERO;
    @Getter
    private int accidentCountAllTime;
    private final int yearDays = 365;
    @Value("${country}")
    private String country;
    @Value("${currency}")
    private String currency;


    public void start(int year) {
        log.info("Действие происходит в стране {}", country);
        for (int i = 0; i < year; i++) {
            startYear();
        }
        log.info("Количество инцидентов за всю работу станции: {} ", accidentCountAllTime);
    }

    public void startYear() {
        BigDecimal kilowattHoursOfYear = BigDecimal.ZERO;
        log.info("Атомная станция начала работу");
        for (int i = 0; i < yearDays; i++) {
            try {
                BigDecimal kilowattHoursOfDay = reactorDepartment.run();
                kilowattHoursOfYear = kilowattHoursOfYear.add(kilowattHoursOfDay);
                reactorDepartment.stop();
            } catch (NuclearFuelIsEmptyException e) {
                log.warn("Происходят работы на атомной станции! Электричества нет!");
            } catch (ReactorWorkException e) {
                log.warn(e.getMessage());
            }
        }
        log.info("Атомная станция закончила работу.");
        log.info("За год Выработано: {} киловатт/часов", kilowattHoursOfYear);
        log.info("Количество инцидентов за год: {} ", securityDepartment.getCountAccidents());
        totalAmountOfEnergyGenerated = totalAmountOfEnergyGenerated.add(kilowattHoursOfYear);
        log.info("Доход за год составил: {} {}", economicDepartment.
                computeYearIncomes(kilowattHoursOfYear.longValue()).
                setScale(2, RoundingMode.HALF_EVEN), currency);
        securityDepartment.reset();
    }

    public void incrementAccident(int count) {
        accidentCountAllTime += count;
    }
}
