package com.javaacademy.nuclear_station;

import com.javaacademy.nuclear_station.department.economic_department.MoroccoEconomicDepartment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.math.RoundingMode;

@SpringBootTest
@ActiveProfiles("morocco")
@TestPropertySource(properties = {"price=0.10", "newPrice=0.15"})
public class MoroccoEconomicDepartmentTest {
    @Autowired
    private MoroccoEconomicDepartment moroccoEconomicDepartment;

    @Test
    @DisplayName("Расчет при электроэнергии ниже 5 миллиардов киловатт-часов")
    public void computeYearIncomes() {
        long countElectricity = 4_000_000_000L;
        BigDecimal expected = BigDecimal.valueOf(countElectricity)
                .multiply(BigDecimal.valueOf(0.10)).setScale(2, RoundingMode.HALF_EVEN);

        BigDecimal result = moroccoEconomicDepartment.computeYearIncomes(countElectricity);

        Assertions.assertEquals(expected, result);

    }

    @Test
    @DisplayName("Расчет при электроэнергии выше 5 миллиардов киловатт-часов")
    public void computeYearIncomes_AboveThreshold() {
        long countElectricity = 6_000_000_000L;
        long increasedElectricity = countElectricity - 5_000_000_000L;

        BigDecimal expected = BigDecimal.valueOf(5_000_000_000L).multiply(BigDecimal.valueOf(0.10))
                .add(BigDecimal.valueOf(increasedElectricity).multiply(BigDecimal.valueOf(0.15)))
                .setScale(2, RoundingMode.HALF_EVEN);

        BigDecimal result = moroccoEconomicDepartment.computeYearIncomes(countElectricity);

        Assertions.assertEquals(expected, result);
    }

}
