package com.cydeo.tests.day13_access_token_specs;

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

public class BookItAccessTokenTest {

    String url = "https://cybertek-reservation-api-qa3.herokuapp.com/sign";

    /**
     *  Given accept type is Json
     *     And Query params: email = blyst6@si.edu & password = barbabaslyst
     *     When I send GET request to
     *     Url: https://cybertek-reservation-api-qa3.herokuapp.com/sign
     *     Then status code is 200
     *     And accessCode should be present
     */

    @DisplayName("GET /sign - request authorization token")
    @Test
    public void bookItLoginTest() {

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("email", "blyst6@si.edu")
                .and().queryParam("password", "barbabaslyst")
                .when().get(url).prettyPeek();

        assertThat(response.statusCode(), is(200));
        String accessToken = response.path("accessToken");
        System.out.println(accessToken);
        assertNotNull(accessToken);


    }


}
