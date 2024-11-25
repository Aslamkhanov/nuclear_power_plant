package com.javaacademy.nuclear_station;

import com.javaacademy.nuclear_station.department.ReactorDepartment;
import com.javaacademy.nuclear_station.department.SecurityDepartment;
import com.javaacademy.nuclear_station.department.economic_department.EconomicDepartment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.math.RoundingMode;

@SpringBootTest
@ActiveProfiles("france")
@TestPropertySource(properties = {"country=FRANCE", "currency=EURO"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class NuclearStationTest {
    private final static long TOTAL_ENERGY_IN_THREE_YEARS = 10_850_000_000L;
    @Autowired
    private SecurityDepartment securityDepartment;
    @Autowired
    private NuclearStation nuclearStation;
    @Autowired
    private ReactorDepartment reactorDepartment;
    @Autowired
    private EconomicDepartment economicDepartment;

    @Test
    @DisplayName("Расчет количества выработанной энергии и количества инцидентов за 3 года")
    public void startYearSuccess() {
        BigDecimal expectedEnergy = BigDecimal.ZERO;
        nuclearStation.start(3);
        BigDecimal resultTotalEnergyGenerated = nuclearStation.getTotalAmountOfEnergyGenerated().
                setScale(2, RoundingMode.HALF_EVEN);
        expectedEnergy = BigDecimal.valueOf(TOTAL_ENERGY_IN_THREE_YEARS)
                .setScale(2, RoundingMode.HALF_EVEN);

        int resultAccidentCountAllTime = nuclearStation.getAccidentCountAllTime();
        int expectedCount = 10;

        Assertions.assertEquals(expectedEnergy, resultTotalEnergyGenerated);
        Assertions.assertEquals(expectedCount, resultAccidentCountAllTime);
    }

    @Test
    @DisplayName("Работа счетчика Аварий")
    void successIncrementAccident() {
        nuclearStation.incrementAccident(1);
        int result = nuclearStation.getAccidentCountAllTime();
        int expected = 1;
        Assertions.assertEquals(expected, result);
    }
}
