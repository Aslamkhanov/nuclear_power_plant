package com.javaacademy.nuclear_station;

import com.javaacademy.nuclear_station.department.ReactorDepartment;
import com.javaacademy.nuclear_station.department.SecurityDepartment;
import com.javaacademy.nuclear_station.exceptions.NuclearFuelIsEmptyException;
import com.javaacademy.nuclear_station.exceptions.ReactorWorkException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("morocco")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReactorDepartmentTest {
    @Autowired
    private ReactorDepartment reactorDepartment;
    @MockBean
    private SecurityDepartment securityDepartment;

    @Test
    @DisplayName("Выбрасывает исключение NuclearFuelIsEmptyException")
    public void runWhenFuelIsEmptyThrowsNuclearFuelIsEmptyException()
            throws NuclearFuelIsEmptyException, ReactorWorkException {
        for (int i = 0; i < 99; i++) {
            reactorDepartment.run();
            reactorDepartment.stop();
        }
        NuclearFuelIsEmptyException exception = assertThrows(NuclearFuelIsEmptyException.class,
                () -> {
                    reactorDepartment.run();
                });
        Mockito.verify(securityDepartment).addAccident();
    }

    @Test
    @DisplayName("Выбрасывает исключение ReactorWorkException")
    public void runException() throws NuclearFuelIsEmptyException, ReactorWorkException {
        reactorDepartment.run();
        ReactorWorkException exception = assertThrows(ReactorWorkException.class, () -> {
            reactorDepartment.run();
        });
        assertEquals("Реактор уже работает", exception.getMessage() );
        Mockito.verify(securityDepartment).addAccident();
    }

@Test
@DisplayName("При первом вызове stop(), выбрасывается исключение")
public void testStop_WhenReactorAlreadyStopped_ThrowsReactorWorkException() {
    ReactorWorkException exception = assertThrows(ReactorWorkException.class, () -> {
        reactorDepartment.stop();
    });
    assertEquals("Реактор уже выключен", exception.getMessage());
    Mockito.verify(securityDepartment).addAccident();
}

    @Test
    @DisplayName("При повторном вызове stop(), выбрасывается исключение")
    public void testStop_WhenReactorIsRunning_StopsSuccessfully()
            throws ReactorWorkException, NuclearFuelIsEmptyException {
        reactorDepartment.run();
        reactorDepartment.stop();

        ReactorWorkException exception = assertThrows(ReactorWorkException.class, () -> {
            reactorDepartment.stop();
        });
        assertEquals("Реактор уже выключен", exception.getMessage());
    }
}
