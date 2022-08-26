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
import com.cydeo.util.SpartanSecureTestBase;

public class SpartanBasicAuthTest extends SpartanSecureTestBase {
    /**
     given accept type is json
     and basic auth credentials are admin , admin
     When user sends GET request to /spartans
     Then status code is 200
     And content type is json
     And print response
     */

    @DisplayName("GET /spartans with basic authorization")
    @Test
    public void getSpartansWithAuthTest(){

        given().accept(ContentType.JSON)
                .and().auth().basic("admin", "admin")
                .when().get("/spartans")
                .then().assertThat().statusCode(200)
                .and().contentType(ContentType.JSON)
                .and().log().all();
    }

    /**
     given accept type is json
     When user sends GET request to /spartans
     Then status code is 401
     And content type is json
     And message is "Unauthorized"
     And print log response
     */

    @Test
    public void getSpartanWithAuthTestWithNegative(){

        given().accept(ContentType.JSON)
                .when().get("/spartans")
                .then().statusCode(401)
                .and().contentType(ContentType.JSON)
                .and().body("message", equalTo("Unauthorized"))
                .and().log().all();


    }

}
