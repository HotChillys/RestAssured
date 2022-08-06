package com.cydeo.tests.day04_path_jsonpath;

import com.cydeo.util.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class HRApiGetTest {

    @BeforeEach // @Before in Junit 4
    public void setUp(){
       baseURI = ConfigurationReader.getProperty("hr.api.url");
    }


    /*
     * Given accept type is json
     * When user send get request to /ords/hr/regions
     * Status code should be 200
     * Content type should be "application/json"
     * And body should contain "Europe"
     */
    @Test
    public void getRegionTest(){
//        given().accept(ContentType.JSON)
//                .when().get("/regions")
//                .then().assertThat().statusCode(200)
//                .and().assertThat().contentType("application/json")
//                .and().assertThat().body("region_id", equalTo("Europe"));

        Response response = given().accept(ContentType.JSON)
                            .when().get("/regions");
        response.prettyPrint();

        assertEquals(200, response.statusCode());

        assertEquals(ContentType.JSON.toString(), response.contentType());

        assertTrue(response.body().asString().contains("Europe"));
    }


    /*
     * Given accept type is json
     And Path param "region_id" value is 1
     When user send get request to /ords/hr/regions/{region_id}
     Status code should be 200
     Content type should be "application/json"
     And body should contain "Europe"
     */
    @DisplayName("GET region/{region_id} / path param")
    @Test
    public void getSingleRegionPathParamTest(){

        Response response = given().log().all().accept(ContentType.JSON)
                .and().pathParam("region_id", 1)
                .when().get("/regions/{region_id}");

        response.prettyPrint();

        assertEquals(HttpStatus.SC_OK, response.statusCode());

        assertEquals("application/json", response.contentType());

        assertTrue(response.body().asString().contains("Europe"));
    }


/*
 * Given accept type is json
 * And query param q={"region_name": "Americas"}
 * When user send get request to /ords/hr/regions
 * Status code should be 200
 * Content type should be "application/json"
 * And region name should be "Americas"
 * And region id should be "2"
 */

    @DisplayName("GET /region?q={\"region_name\": \"Americas\"}")
    @Test
    public void getRegionWithQueryParamTest(){

        Response response = given().log().all().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_name\": \"Americas\"}")
                .when().get("/regions");

        response.prettyPrint();

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals("application/json", response.contentType());

        assertTrue(response.body().asString().contains("Americas"));
        assertTrue(response.body().asString().contains("2"));

    }

}
