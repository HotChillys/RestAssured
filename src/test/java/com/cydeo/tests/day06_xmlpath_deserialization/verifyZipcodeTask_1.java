package com.cydeo.tests.day06_xmlpath_deserialization;

import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import com.cydeo.pojo.Place;
import com.cydeo.pojo.ZipInfo;
import com.cydeo.util.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;

public class verifyZipcodeTask_1 {
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
    public void verifyZip(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("postal-code", 22031)
                .when().get("/us/{postal-code}").prettyPeek();

        assertEquals(200, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());
        assertEquals("cloudflare", response.getHeader("Server"));
        assertTrue(response.getHeaders().hasHeaderWithName("Report-To"));

        //option 1 --> response as
        ZipInfo zipInfo = response.as(ZipInfo.class);
        System.out.println(zipInfo);
        assertThat("22031", is(zipInfo.getPostCode()));
        assertThat("United States", is(zipInfo.getCountry()));
        assertThat("US", is(zipInfo.getCountryAbbreviation()));

        assertThat("Fairfax", is(zipInfo.getPlaces().get(0).getPlaceName()));
        assertThat("Virginia", is(zipInfo.getPlaces().get(0).getState()));
        assertThat("38.8604", is(zipInfo.getPlaces().get(0).getLatitude()));

        System.out.println("====JsonPath Get Object");

        // option 2
        ZipInfo zipInfoJsonPath = response.jsonPath().getObject("", zipInfo.getClass());
        System.out.println(zipInfoJsonPath);
        assertThat("22031", is(zipInfo.getPostCode()));
        assertThat("United States", is(zipInfo.getCountry()));
        assertThat("US", is(zipInfo.getCountryAbbreviation()));

        assertThat("Fairfax", is(zipInfo.getPlaces().get(0).getPlaceName()));
        assertThat("Virginia", is(zipInfo.getPlaces().get(0).getState()));
        assertThat("38.8604", is(zipInfo.getPlaces().get(0).getLatitude()));
    }


}
