package com.cydeo.tests.day14_specifications;

import com.cydeo.util.BookItAPITestBase;
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
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import com.cydeo.util.SpartanSecureTestBase;
public class BookItSpecTest extends BookItAPITestBase {
    /**
     Given accept type is json
     And header Authorization is access_token of teacher
     When I send GET request to /api/teachers/me
     Then status code is 200
     And content type is json
     And teacher info is presented in payload
     */
    @Test
    public void teacherInfoTest() {

        Map<String, ?> teacherMap = given().spec(teacherReqSpec)
                .when().get("api/teachers/me")
                .then().spec(responseSpec)
                .and().extract().body().as(Map.class);

        Assertions.assertEquals(1816, teacherMap.get("id"));
        Assertions.assertEquals("Barbabas", teacherMap.get("firstName") );
        Assertions.assertEquals("Lyst", teacherMap.get("lastName") );
        Assertions.assertEquals("teacher", teacherMap.get("role") );

        //by using hamcrest matchers
        given().spec(teacherReqSpec)
                .when().get("/api/teachers/me")
                .then().spec(responseSpec)
                .and().body("id",       is(1816),
                        "firstName",is("Barbabas"),
                        "lastName" , is("Lyst"),
                        "role",      is("teacher"));

    }
}
