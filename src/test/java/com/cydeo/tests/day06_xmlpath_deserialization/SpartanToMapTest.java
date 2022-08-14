package com.cydeo.tests.day06_xmlpath_deserialization;

import com.cydeo.util.SpartanTestBase;
import org.junit.jupiter.api.Test;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import io.restassured.http.ContentType;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class SpartanToMapTest extends SpartanTestBase {

    /**
     Given accept type is application/json
     And Path param id = 10
     When i send GET request to /api/spartans
     Then status code is 200
     And content type is json
     And spartan data matching:
     id > 10
     name>Lorenza
     gender >Female
     phone >3312820936
     */

    @DisplayName("get /SPARTANS/{ID} PATH MAP")
    @Test
    public void spartanTpMapTest(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 10)
                .when().get("/spartans/{id}");

        response.prettyPrint();

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());

        //convert json to map object. DE-SERIALIZATION
        Map<String, Object> spartanMap = response.body().as(Map.class);
        System.out.println(spartanMap);

        System.out.println("Keys = " + spartanMap.keySet());

        assertEquals(10, spartanMap.get("id"));
        assertEquals("Lorenza", spartanMap.get("name"));
        assertEquals("Female", spartanMap.get("gender"));
        assertEquals(3312820936L, spartanMap.get("phone"));
    }



}
