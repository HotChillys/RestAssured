package com.cydeo.tests.day03_parameters;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class SpartanPathParameter {

    String url = "http://3.93.242.50:8000/api/spartans";

      /*   Given Accept type is Json
          And Id parameter value is 5
          When user sends GET request to /api/spartans/{id}
          Then response status code should be 200
          And response content-type: application/json
          And "Blythe" should be in response payload(body)
       */

    @DisplayName("GET /api/spartans/{id}")
    @Test
    public void getSingleSpartan(){
        given().accept(ContentType.JSON)
                .when().get(url + "/5");

        //or

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 5)
                .when().get(url + "/{id}");

        response.prettyPrint();
        System.out.println("status code = " + response.statusCode());
        assertEquals(200, response.statusCode());

        assertEquals(HttpStatus.SC_OK, response.statusCode());

        System.out.println("content type = " + response.contentType());
        System.out.println("content type = " + response.getHeader("content-type"));

        assertTrue(response.asString().contains("Blythe"));

    }


    /*
     Given accept type is Json
    And Id parameter value is 500
    When user sends GET request to /api/spartans/{id}
    Then response status code should be 404
    And response content-type: application/json
    And "Not Found" message should be in response payload
 */
    @DisplayName("GET /api/spartans/{id} with missing id")
    @Test
    public void getSingleSpartanNotFound(){

        int id = 500;

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id" , id)
                .when().get(url + "/{id}");

        System.out.println("status code = " + response.statusCode());
        assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());

        assertEquals(ContentType.JSON.toString(), response.contentType());

        response.prettyPeek();

        assertTrue(response.asString().contains("Not Found"));

    }







}
