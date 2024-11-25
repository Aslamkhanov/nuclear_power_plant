package com.javaacademy.nuclear_station;

import com.javaacademy.nuclear_station.department.SecurityDepartment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("morocco")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SecurityDepartmentTest {
    @Autowired
    private SecurityDepartment securityDepartment;

    @Test
    public void addAccidentSuccess() {
        securityDepartment.addAccident();
        int result = securityDepartment.getCountAccidents();
        int expected = 1;
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void resetSuccess() {
        securityDepartment.addAccident();
        securityDepartment.reset();
        int result = securityDepartment.getCountAccidents();
        int expected = 0;
        Assertions.assertEquals(expected, result);
    }
}
