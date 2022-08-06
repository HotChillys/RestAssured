package com.cydeo.tests.day04_path_jsonpath;

import com.cydeo.util.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;
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

}
