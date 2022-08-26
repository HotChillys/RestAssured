package com.cydeo.tests.day13_access_token_specs;

import com.cydeo.util.BookItAPITestBase;
import io.restassured.response.Response;
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
import org.junit.jupiter.api.Test;

import javax.annotation.meta.When;
public class BookItApiTest extends BookItAPITestBase {
    /**
     Given accept type is json
     And header Authorization is access_token of teacher
     When I send GET request to /api/campuses
     Then status code is 200
     And content type is json
     And campus location info is presented in payload
     */

    @Test
    public void getAllCampusesTest() {

        String accessToken = getAccessToken(ConfigurationReader.getProperty("teacher_email"),
                ConfigurationReader.getProperty("teacher_password"));

        Response response = given().accept(ContentType.JSON)
                .and().header("Authorization", accessToken)
                .when().get("api/campuses").prettyPeek();

        assertThat(response.statusCode(), is(200));
        assertThat(response.contentType(), is(ContentType.JSON.toString()));
        assertThat(response.path("location"), hasItems("VA", "IL"));
    }

    @Test
    public void ilCampusTest() {
        /**
         * Given accept type is Json
         * And header Authorization is access token of team leader
         * And path param "campus_location" is "IL"
         * When I send GET request to "/api/campuses/{campus_location}
         * Then status code is 200
         * And content type is json
         * And location is "IL"
         * And rooms names has items "google" , "apple", "microsoft", "tesla"
         */

        String accessToken = getAccessToken(ConfigurationReader.getProperty("team_leader_email"),
                ConfigurationReader.getProperty("team_leader_password"));

        given().accept(ContentType.JSON)
                .and().header("Authorization", accessToken)
                .and().pathParam("campus_location", "IL")
                .when().get("/api/campuses/{campus_location}")
                .prettyPeek()
                .then().statusCode(200)
                .and().contentType(ContentType.JSON)
                .and().body("location", equalTo("IL"))
                .and().body("clusters[0].rooms.name", hasItems("google" , "apple", "microsoft", "tesla"));

    }
    @Test
    public void roomInfoTest() {
        /**
         * Given accept type is Json
         * And header Authorization is access token of team leader
         * And path param "room_name" is "mit"
         * When I send GET request to "/api/rooms/{room_name}
         * Then status code is 200
         * And content type is json
         body matches data:
         {
         "id": 111,
         "name": "mit",
         "description": "mens et manus",
         "capacity": 6,
         "withTV": true,
         "withWhiteBoard": true
         }
         */

        String accessToken = getAccessToken(ConfigurationReader.getProperty("team_member_email"),
                ConfigurationReader.getProperty("team_member_password"));

     Map roomInfoMap =    given().accept(ContentType.JSON)
                .and().pathParam("name", "mit")
                .and().header("Authorization", accessToken)
                .when().get("/api/rooms/{name}").prettyPeek()
                .then().statusCode(200)
                .and().contentType(ContentType.JSON)
                .and().extract().body().as(Map.class);

        System.out.println("Room info Map = " + roomInfoMap);

        assertThat(roomInfoMap.get("id"), is(111));
        assertThat(roomInfoMap.get("name"), is("mit"));
        assertThat(roomInfoMap.get("capacity"), is(6));
        assertThat(roomInfoMap.get("withTV"), is(true));
        assertThat(roomInfoMap.get("description"), is("mens et manus"));
        assertThat(roomInfoMap.get("withWhiteBoard"), is(true));

    }



}
