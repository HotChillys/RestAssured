package com.cydeo.tests.day11_put_patch_request;

import com.cydeo.util.SpartanTestBase;
import com.cydeo.util.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import com.cydeo.util.SpartansRestUtil;
import com.cydeo.util.SpartanTestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;
import java.util.Map;
public class SpartanPUTRequest extends SpartanTestBase {


/**
     * Given accept type is json
     * And content type is json
     * And path param id is 15
     * And json body is
     * {
     *     "gender": "Male",
     *     "name": "PutRequest"
     *     "phone": 1234567425
     * }
     * When i send PUT request to /spartans/{id}
     * Then status code 204
     */

    @DisplayName("PUT /spartans/{id}")
    @Test
    public void updateSpartanTest(){

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gender", "Male");
        requestMap.put("name", "PutRequest");
        requestMap.put("phone", 1234567888L);

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().pathParam("id", 23)
                .and().body(requestMap)
                .when().put("/spartans/{id}").prettyPeek();

        response.prettyPrint();
        System.out.println("status code = " + response.statusCode());

        assertThat(response.statusCode(), is(204));


    }
}
