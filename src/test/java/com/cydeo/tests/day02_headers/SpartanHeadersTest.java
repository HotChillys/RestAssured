package com.cydeo.tests.day02_headers;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


public class SpartanHeadersTest {

    String url = "http://3.93.242.50:8000/api/spartans";

    /**
     * ----------------request----------
     * Given Accept type is application/json
     • When I send a GET request to
     ----------------------------------------
     • spartan_base_url/api/spartans
     • Then Response STATUS CODE must be 200
     • And I Should see all Spartans data in JSON format
     */

    @Test
    public void getAllSpartanHeaderTest(){

        when().get(url)
                .then().assertThat().statusCode(200)  //request part
                .and().contentType(ContentType.JSON);
           //or .and().contentType("application/json");

    }


    /**
     * ----------------request----------
     * Given Accept type is application/xml
     • When I send a GET request to
     ----------------------------------------
     • spartan_base_url/api/spartans
     • Then Response STATUS CODE must be 200
     • And I Should see all Spartans data in XML format
     */

    @Test
    public void acceptTypeHeaderTest(){
        given().accept(ContentType.XML)
                .when().get(url)
                .then().assertThat()
                .statusCode(200)
                .and().contentType(ContentType.XML);
    }



    /**
     * Given Accept type is application/json
     • When I send a GET request to
     -----------------------------
     • spartan_base_url/api/spartans
     • Then Response STATUS CODE must be 200
     • And read headers
     */
    @DisplayName("GET /api/spartans - read headers")
    @Test
     public void readResponseHeader(){
        Response response = given().accept(ContentType.JSON)
                .when().get(url);

        System.out.println("Date header = " + response.getHeader("Date"));
        System.out.println("Content type = " + response.getHeader("Content-Type"));
        System.out.println("Connection = " + response.getHeader("Connection"));

        // read all the headers
        System.out.println(response.getHeaders());

        // ensure header "keep-alive" is present
        assertNotNull(response.getHeader("keep-alive"));

    }

}
