package com.cydeo.tests.day05_path_jsonpath;

import com.cydeo.util.ConfigurationReader;
import com.cydeo.util.SpartanTestBase;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class SpartansJsonPathTest extends SpartanTestBase {

    /**
     * Given accept is json
     * And path param id is 13
     * When I send get request to /api/spartans/{id}
     * ----------
     * Then status code is 200
     * And content type is json
     * And id value is 13
     * And name is "Jaimie"
     * And gender is "Female"
     * And phone is "7842554879"
     */


    @DisplayName("GET /api/spartans/{id} -> jsonPath")
    @Test
    public void getSpartanJsonPathTest() {

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 13)
                .when().get("/spartans/{id}");

        assertEquals(HttpStatus.SC_OK, response.statusCode());

        assertEquals("application/json", response.contentType());
        assertEquals("application/json", response.getHeader("Content-Type"));

        System.out.println("content type = " + response.contentType());
        assertEquals("application/json", response.contentType());
        assertEquals("application/json", response.getHeader("content-type"));

        /**
         {
         "id": 13,
         "name": "Jaimie",
         "gender": "Female",
         "phone": 7842554879
         }
         */

        JsonPath jsonPath = response.jsonPath();

        assertEquals(13, jsonPath.getInt("id"));
        assertEquals("Jaimie", jsonPath.getString("name"));
        assertEquals("Female", jsonPath.getString("gender"));
        assertEquals(7842554879L, jsonPath.getLong("phone"));




    }



    }





