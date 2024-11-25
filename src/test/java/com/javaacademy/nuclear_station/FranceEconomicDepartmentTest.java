package com.javaacademy.nuclear_station;

import com.javaacademy.nuclear_station.department.economic_department.FranceEconomicDepartment;
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
@ActiveProfiles("france")
@TestPropertySource(properties = {"price=0.5", "percentage=0.99"})
public class FranceEconomicDepartmentTest {
    private final static long ONE_BILLION = 1_000_000_000L;
    @Autowired
    private FranceEconomicDepartment franceEconomicDepartment;

    @Test
    @DisplayName("Доход при 1 миллиарде")
    public void computeYearIncomesOneBillionSuccess() {
        long countElectricity = 1_000_000_000L;
        BigDecimal expected = BigDecimal.valueOf(countElectricity)
                .multiply(BigDecimal.valueOf(0.5))
                .setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal result = franceEconomicDepartment.computeYearIncomes(countElectricity);
        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Доход при 2 миллиарде")
    public void computeYearIncomesTwoBillionSuccess() {
        long countElectricity = 2_000_000_000L;
        BigDecimal expected1 = BigDecimal.valueOf(ONE_BILLION)
                .multiply(BigDecimal.valueOf(0.5));

        BigDecimal expected2 = BigDecimal.valueOf(ONE_BILLION)
                .multiply(BigDecimal.valueOf(0.5))
                .multiply(BigDecimal.valueOf(0.99));
        BigDecimal expected = expected1.add(expected2).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal result = franceEconomicDepartment.computeYearIncomes(countElectricity);
        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Доход при 3 миллиарде")
    public void computeYearIncomesThreeBillionKilowattSuccess() {
        long countElectricity = 3_000_000_000L;
        BigDecimal expected = BigDecimal.valueOf(ONE_BILLION)
                .multiply(BigDecimal.valueOf(0.5))
                .add(BigDecimal.valueOf(ONE_BILLION)
                        .multiply(BigDecimal.valueOf(0.5))
                        .multiply(BigDecimal.valueOf(0.99)))
                .add(BigDecimal.valueOf(ONE_BILLION)
                        .multiply(BigDecimal.valueOf(0.5))
                        .multiply(BigDecimal.valueOf(0.99).multiply(BigDecimal.valueOf(0.99))))
                .setScale(2, RoundingMode.HALF_EVEN);

        BigDecimal result = franceEconomicDepartment.computeYearIncomes(countElectricity);

        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Доход при нуле")
    public void computeYearIncomes_ZeroKilowatt() {
        long countElectricity = 0L;
        BigDecimal expected = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);

        BigDecimal result = franceEconomicDepartment.computeYearIncomes(countElectricity);

        Assertions.assertEquals(expected, result);
    }

}
