package com.cydeo.tests.day04_path_jsonpath;

import com.cydeo.util.SpartanTestBase;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class SpartansPathMethodTest extends SpartanTestBase {


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

@DisplayName("GET /api/spartans/{id} and path()")
@Test
    public void readSpartanJsonUsingPathParamTest(){

    Response response = given().accept(ContentType.JSON)
            .and().pathParam("id", 13)
            .when().get("/spartans/{id}");

    System.out.println("id = " + response.path("id"));
    System.out.println("name = " + response.path("name"));
    System.out.println("gender = " + response.path("gender"));
    System.out.println("phone = " + response.path("phone"));

    assertEquals(HttpStatus.SC_OK, response.statusCode());
    assertEquals("application/json", response.contentType());

    int id = response.path("id");
    assertEquals(13, id);

    assertEquals("Jaimie", response.path("name"));
    assertEquals("Female", response.path("gender"));
    assertEquals(7842554879L, (Long) response.path("phone"));
    assertEquals(7842554879L, Long.valueOf("" + response.path("phone")) ); // or this

}


    /**
     Given accept is json
     When I send get request to /api/spartans
     Then status code is 200
     And content type is json
     And I can navigate json array object
     */

    @DisplayName("GET /api/spartans and path()")
    @Test
    public void readSpartanJsonArrayUsingPathParamTest(){

        Response response = given().accept(ContentType.JSON)
                .when().get("/spartans");

        assertEquals(HttpStatus.SC_OK, response.statusCode());

        assertEquals("application/json", response.contentType());

        System.out.println("first spartan id = " + response.path("id[0]"));

        System.out.println("first spartan name = " + response.path("name[0]"));
        System.out.println("first spartan name = " + response.path("[0].name")); // or

        // print last spartan id and name
        System.out.println("last spartan id = " + response.path("id[-1]"));
        System.out.println("last spartan name = " + response.path("name[-1]"));

        // get all ids into a List
        List<Integer> allIds = response.path("id");
        System.out.println("all ids size = " + allIds.size());
        System.out.println("all ids = " + allIds);

        assertTrue(allIds.contains(22) && allIds.size() == 97);

        // get all names and say hi
        List<String> allNames = response.path("name");
        for (String eachName : allNames) {
            System.out.println("Hello, how are you? " + eachName);
        }

        //or

        allNames.forEach(names -> System.out.println("Good Bye " + names));


    }

}
