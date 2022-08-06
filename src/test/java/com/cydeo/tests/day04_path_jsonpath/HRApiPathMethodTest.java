package com.cydeo.tests.day04_path_jsonpath;

import com.cydeo.util.HRApiTestBase;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class HRApiPathMethodTest extends HRApiTestBase {

    @Test
    public void readCountriesUsingPathTest(){

        Response response = given().accept(ContentType.JSON)
                .when().get("/countries");

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals("application/json", response.contentType());

        System.out.println("First country = " + response.path("items[0].country_id"));
        System.out.println("First country = " + response.path("items[0].country_name"));

        assertEquals("AR", response.path("items[0].country_id"));
        assertEquals("Argentina", response.path("items[0].country_name"));

        // store all country names into a List
        List<String> countryNames = response.path("items.country_name");
        System.out.println(countryNames);

    }

}
