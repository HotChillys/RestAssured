package com.cydeo.tests.day04_path_jsonpath;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class ChainingAndHamcrest {

    String url = "http://3.93.242.50:8000";

    /*
    given accept type is json
    and path param id is 15
    when user sends a get request to api/spartans/{id}
    then status code is 200
    and content type is json
    and json data has following
        "id" = 15,
        "name" = "Meta"
        "gender" = "Female"
        "phone" = 1938695106
     */

    @Test
    public void test_1(){

        // request response chaining
        given().accept(ContentType.JSON)
                .pathParams("id", 15)
                .when().get(url + "/api/spartans/{id}")
                .then().statusCode(200)
                .and().assertThat().contentType("application/json");
    }


    @Test
    public void test_2(){

        given().accept(ContentType.JSON)
                .pathParams("id", 15)
                .when().get(url + "/api/spartans/{id}")
                .then().assertThat().statusCode(200)
                .and().assertThat().contentType("application/json")
                .and().assertThat().body( "id", equalTo(15), "name", equalTo("Meta"), "gender", equalTo("Female"), "phone", equalTo(1938695106L) );


    }


}
