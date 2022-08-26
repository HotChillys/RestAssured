package com.cydeo.tests.day12_jsonschame_authorization;

import com.cydeo.util.SpartanTestBase;
import com.cydeo.util.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class SingleSpartanJsonSchemaValidationTest extends SpartanTestBase {

/**
 * given accept type is json
 * and path param id is 15
 * when I send GEt request to /spartans/{id}
 * then status code is 200
 * And json payload/body matches SingleSpartanSchema.json file
 */

@DisplayName("GET /spartan/{id} Schema")
@Test
    public void singleSpartanSchemaValidationTest() {

    given().accept(ContentType.JSON)
            .and().pathParam("id", 15)
            .when().get("/spartans/{id}")
            .then().statusCode(200)
            .and().body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/json_schema/SingleSpartanSchema.json")))
            .and().log().all();
}

/**
 * given accept type is json
 * when I send GEt request to /spartans
 * then status code is 200
 * And json payload/body matches AllSpartanSchema.json file
 */


@Test
    public void AllSpartanSchemaValidationTest(){

        given().accept(ContentType.JSON)
                .when().get("/spartans")
                .then().statusCode(200)
                .and().body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/json_schema/AllSpartanSchema.json")))
                .log().all();

}


/**
 * given accept type is json
 * and query param is "e" and gender "Female"
 * when I send GEt request to /spartans/search
 * then status code is 200
 * And json payload/body matches SearchSpartanSchema.json file
 */

@Test
    public void SearchSpartanSchemaValidationTest() {

    given().accept(ContentType.JSON)
            .and().queryParam("nameContains", "e")
            .and().queryParam( "gender", "Female")
            .when().get("/spartans/search")
            .then().assertThat().statusCode(200)
            .body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/json_schema/SearchSpartanSchema.json")))
            .log().all();




}




}
