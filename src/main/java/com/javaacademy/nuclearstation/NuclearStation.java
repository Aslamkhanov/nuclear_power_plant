package com.javaacademy.nuclearstation;

import com.javaacademy.nuclearstation.economicdepartment.EconomicDepartment;
import com.javaacademy.nuclearstation.exceptions.NuclearFuelIsEmptyException;
import com.javaacademy.nuclearstation.exceptions.ReactorWorkException;
import com.javaacademy.nuclearstation.reactordepartment.ReactorDepartment;
import com.javaacademy.nuclearstation.securitydepartment.SecurityDepartment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        log.info("Количество инцидентов за год: {} ", securityDepartment.getCountAccidents());
        log.info("Доход за год составил: {} {}", economicDepartment.
                computeYearIncomes(kilowattHoursOfYear.longValue()).
                setScale(2, RoundingMode.HALF_EVEN), currency);
        securityDepartment.reset();
    }

    public void incrementAccident(int count) {
        accidentCountAllTime += count;
    }
}
