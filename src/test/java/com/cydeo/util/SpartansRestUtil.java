package com.cydeo.util;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
public class SpartansRestUtil {

    private static String baseUrl = ConfigurationReader.getProperty("spartan.api.url");

    public static void deleteSpartanById(int spartanId) {

        //send DELETE request {{baseUrl}}/api/spartans/{id}
        given().pathParam("id", spartanId)
                .when().delete(baseUrl + "/spartans/{id}")
                .then().log().all();

    }



}
