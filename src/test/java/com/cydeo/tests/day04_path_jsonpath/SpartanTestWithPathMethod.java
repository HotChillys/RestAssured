package com.cydeo.tests.day04_path_jsonpath;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class SpartanTestWithPathMethod {

    /*
        Given accept type is json
        and path param id is 10
        When user sends a get request to "api/spartans/{id}"
        Then status code is 200
        And content-type is "application/jason;char"
        And response payload values match the following:
            id is 10,
            name is "Lorenza"
            gender is "Female"
            phone is 3312820936
     */

    String url = "http://3.93.242.50:8000";

    @Test
    public void test_1(){

        int id = 10;

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 10)
                .when().get(url + "/api/spartans/{id}");

        assertEquals(200, response.statusCode());

        assertEquals(ContentType.JSON.toString(), response.contentType());



    }



}
