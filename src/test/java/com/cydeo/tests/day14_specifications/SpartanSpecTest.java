package com.cydeo.tests.day14_specifications;

import com.cydeo.util.SpartanSecureTestBase;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;
import com.cydeo.util.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import com.cydeo.util.SpartanSecureTestBase;
import org.junit.jupiter.api.Test;

import javax.annotation.meta.When;

public class SpartanSpecTest extends SpartanSecureTestBase {

    //we don't have to have this because we already declared it in parent class.
    RequestSpecification requestSpec = given().accept(ContentType.JSON)
            .and().auth().basic("admin", "admin");

    ResponseSpecification responseSpec = expect().statusCode(200).
            and().contentType(ContentType.JSON);

    @Test
    public void allSpartansTest() {

        given().spec(requestSpec)
                .when().get("/spartans")
                .then().spec(responseSpec)
                .log().all();
    }


    @Test
    public void singleSpartansTest() {

        given().spec(requestSpec)
                .and().pathParam("id", 24)
                .when().get("/spartans/{id}")
                .then().spec(responseSpec)
                .and().body("name", equalTo("Julio"))
                .log().all();


    }


}
