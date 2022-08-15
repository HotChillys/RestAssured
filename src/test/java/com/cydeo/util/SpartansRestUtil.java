package com.cydeo.util;
import com.cydeo.pojo.Spartan;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
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


    public static Spartan getSpartan() {

        Faker faker = new Faker();
        Spartan spartan = new Spartan();
        spartan.setName(faker.name().firstName());
        int number = faker.number().numberBetween(1, 3);
        spartan.setGender(number == 1 ? "Female" : "Male");
        spartan.setPhone(Long.parseLong(faker.phoneNumber().subscriberNumber(10)));

        return spartan;
    }



}
