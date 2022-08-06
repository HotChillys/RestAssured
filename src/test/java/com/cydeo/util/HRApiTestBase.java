package com.cydeo.util;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class HRApiTestBase {

    @BeforeAll //@BeforeClass in junit 4
    public static void setUp() {
        RestAssured.baseURI = ConfigurationReader.getProperty("hr.api.url");
    }

}
