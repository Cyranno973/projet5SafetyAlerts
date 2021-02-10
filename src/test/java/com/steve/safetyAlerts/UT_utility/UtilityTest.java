package com.steve.safetyAlerts.UT_utility;

import com.steve.safetyAlerts.utility.CalculateAge;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class UtilityTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void Calculate() throws Exception {
        int age;
        String ageParam = "08/28/1992";
        age = CalculateAge.personAge(ageParam);
        assertEquals(28, age);
        System.out.println(age);
    }
}

