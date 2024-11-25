package com.javaacademy.nuclear_station.department.economic_department;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@Profile(value = "morocco")
public class MoroccoEconomicDepartment extends EconomicDepartment {
    private static final long FIVE_BILLION_KILOWATT_HOURS = 5_000_000_000L;
    @Value("${price}")
    private BigDecimal price;
    @Value("${newPrice}")
    private BigDecimal newPrice;

    @Override
    public BigDecimal computeYearIncomes(long countElectricity) {
        BigDecimal totalRevenue = BigDecimal.ZERO;
        if (countElectricity <= FIVE_BILLION_KILOWATT_HOURS) {
            totalRevenue = BigDecimal.valueOf(countElectricity).multiply(price);
        } else {
            long increasedElectricity = countElectricity - FIVE_BILLION_KILOWATT_HOURS;
            totalRevenue = BigDecimal.valueOf(FIVE_BILLION_KILOWATT_HOURS).multiply(price).
                    add(BigDecimal.valueOf(increasedElectricity).multiply(newPrice));
        }
        return totalRevenue.setScale(2, RoundingMode.HALF_EVEN);
    }
}
