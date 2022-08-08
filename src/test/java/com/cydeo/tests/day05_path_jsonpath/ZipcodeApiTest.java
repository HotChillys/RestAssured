package com.cydeo.tests.day05_path_jsonpath;

import com.cydeo.util.ConfigurationReader;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class ZipcodeApiTest {


    /*
    Given accept type is json
    and country path param value is "us"
    and postal code path param value is 22102
    When I send get request to http://api.zippopotam.us/{country}/{postal-code}
    Then status code is 200
    Then "post code" is "22102"
    And  "country" is "United States"
    And "place name" is "Mc Lean"
    And  "state" is "Virginia"
     */

    @BeforeAll
    public static void zipcode_api_url() {
        // setting up base url
        baseURI = ConfigurationReader.getProperty("zipcode.api.url");
    }

    @DisplayName("GET us/zipcode - JsonPath")
    @Test
    public void zipcode_test() {

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("country", "us")
                .and().pathParam("postal-code", "22102")
                .when().get("/{country}/{postal-code}");

        response.prettyPrint();

        assertEquals(200, response.statusCode());

        //assign response json payload/body to JsonPath
        JsonPath jsonPath = response.jsonPath();


        System.out.println("country name = " + jsonPath.getString("country"));
        assertEquals("United States", jsonPath.getString("country"));

        // navigate the json and print post code value
        System.out.println("post code = " + jsonPath.getString("'post code'"));
        assertEquals("22102", jsonPath.getString("'post code'"));

        assertEquals("Mc Lean", jsonPath.getString("places[0].'place name'"));
        assertEquals("Virginia", jsonPath.getString("places[0].state"));

        verifyZipCode(jsonPath, "22102");
    }


    public void verifyZipCode(JsonPath jsonPath, String expZipCode) {
        assertEquals(expZipCode, jsonPath.getString("'post code'"));
    }
}
