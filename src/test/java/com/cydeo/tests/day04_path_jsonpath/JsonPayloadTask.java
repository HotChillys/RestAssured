package com.cydeo.tests.day04_path_jsonpath;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class JsonPayloadTask {

    String url = "http://3.93.242.50:8000";

/*
    Given accept type is Jason
    And path param id is 11
    When user sends a get request to /api/spartans/{id}
    Then status code is  200
    And content type is Json
    And "id": 11,
        "name": "Nona",
        "gender": "Female"
        "phone": 7959094216
 */

    @Test
    public void test_1(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParams("id", 11)
                .when().get(url + "/api/spartans/{id}");

        assertEquals(200, response.statusCode());
        // how to read value with path() method
        int id = response.path("id");
        System.out.println("id = " + id );

        // how to read from json path
        JsonPath jsonData = response.jsonPath();

        int id1 = jsonData.getInt("id");
        String name = jsonData.getString("name");
        String gender = jsonData.getString("gender");
        long phone = jsonData.getLong("phone");

        System.out.println("id = " + id1);
        System.out.println("name = " + name);
        System.out.println("gender = " + gender);
        System.out.println("phone number = " + phone);

        // verify json payload with json Path
        assertEquals(11, id1);
        assertEquals("Nona", name);
        assertEquals("Female" , gender);
        assertEquals(7959094216L, phone);


    }


}
