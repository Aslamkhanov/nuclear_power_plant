package com.javaacademy.nuclearstation.economicdepartment;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Component
@Profile(value = "france")
public class FranceEconomicDepartment extends EconomicDepartment {
    private final static long BILLION = 1_000_000_000L;
    @Value("${price}")
    private BigDecimal price;
    @Value("${percentage}")
    private BigDecimal percentage;

    @Override
    public BigDecimal computeYearIncomes(long countElectricity) {
        BigDecimal totalRevenue = BigDecimal.ZERO;
        int indexNextBillionKilowatt = 0;

        while (countElectricity > 0) {
            long currentBatch = Math.min(countElectricity, BILLION);
            totalRevenue = totalRevenue.add(computeIncomes(currentBatch, indexNextBillionKilowatt));
            countElectricity -= currentBatch;
            indexNextBillionKilowatt++;
        }
        return totalRevenue.setScale(2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal computeIncomes(long countElectricity, int indexNextBillionKilowatt) {
        return BigDecimal.valueOf(countElectricity)
                .multiply(price)
                .multiply(percentage.pow(indexNextBillionKilowatt))
                .setScale(2, RoundingMode.HALF_EVEN);
    }
}
