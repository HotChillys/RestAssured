package com.cydeo.tests.day08_hamcrest;

import com.cydeo.util.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class HW_Zipcode {

    @BeforeAll
    public static void zip_code_baseUrl(){
        baseURI = ConfigurationReader.getProperty("zipcode.api.url");
    }

    /**
     * Given Accept application/json
     * And path zipcode is 22031
     * When I send a GET request to /us endpoint
     * Then status code must be 200
     * And content type must be application/json
     * And Server header is cloudflare
     * And Report-To header exists
     * And body should contains following information
     * post code is 22031
     * country  is United States
     * country abbreviation is US
     * place name is Fairfax
     * state is Virginia
     * latitude is 38.8604
     */

    @Test
    public void verifyByZipcodeAddress() {
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("country", "us")
                .and().pathParam("zipcode", "22031")
                .when().get("/{country}/{zipcode}");
        response.prettyPrint();

        Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
        Assertions.assertEquals(ContentType.JSON.toString(), response.contentType());
        Assertions.assertEquals("cloudflare", response.getHeader("Server"));
        Assertions.assertNotNull(response.getHeader("Report-to"));

        JsonPath jsonPath = response.jsonPath();

        assertThat(jsonPath.getString("'post code'"), is("22031"));
        assertThat(jsonPath.getString("country"), is("United States"));
        assertThat(jsonPath.getString("'country abbreviation'"), is("US"));
        assertThat(jsonPath.getString("places[0].'place name'"), is("Fairfax"));
        assertThat(jsonPath.getString("places[0].state"), is("Virginia"));
        assertThat(jsonPath.getString("places[0].latitude"), is("38.8604"));
    }


    /**
     * Given Accept application/json
     * And path zipcode is 50000
     * When I send a GET request to /us endpoint
     * Then status code must be 404
     * And content type must be application/json
     */
    @Test
    public void verifyInvalidZipcode(){

      given().accept(ContentType.JSON)
              .and().pathParam("country", "us")
              .and().pathParam("zipcode", "50000")
              .when().get("/{country}/{zipcode}")
              .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
              .and().assertThat().contentType(ContentType.JSON);

    }


    /**
     * Given Accept application/json
     * And path state is va
     * And path city is farifax
     * When I send a GET request to /us endpoint
     * Then status code must be 200
     * And content type must be application/json
     * And payload should contains following information
     *     country abbreviation is US
     *     country  United States
     *     place name  Fairfax
     *     each places must contains fairfax as a value
     *     each post code must start with 22
     */
    @Test
    public void verify_using_query_param(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("country", "us")
                .and().pathParam("state", "VA")
                .and().pathParam("city", "Fairfax")
                .when().get("/{country}/{state}/{city}");
     //   response.prettyPrint();

        assertThat(response.jsonPath().getString("'country abbreviation'"), is("US"));
        assertThat(response.jsonPath().getString("country"), is("United States"));
        assertThat(response.jsonPath().getString("'place name'"), is("Fairfax"));


        List<String> list = response.jsonPath().getList("places");

        for (int i = 0; i < list.size(); i++) {

            String placeName = response.jsonPath().getString("places[" +i+ "].'place name'");
            assertTrue(placeName.contains("Fairfax"));

            String postCode = response.jsonPath().getString("places[" +i+ "].'post code'");
            assertTrue(postCode.startsWith("22"));
        }


    }


}
