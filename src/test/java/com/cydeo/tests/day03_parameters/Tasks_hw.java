package com.cydeo.tests.day03_parameters;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class Tasks_hw {

    String url = "https://jsonplaceholder.typicode.com/posts";

    /*
    - Given accept type is Json
    - When user sends request to https://jsonplaceholder.typicode.com/posts
    -Then print response body
    - And status code is 200
    - And Content - Type is Json
     */
    @DisplayName("GET Json Place Holder")
    @Test
    public void test_1(){

        Response response = given().accept(ContentType.JSON)
                .when().get(url);

        response.prettyPrint();

        assertEquals(200, response.statusCode());
        assertTrue(response.contentType().contains(ContentType.JSON.toString()));

    }

    /*
    - Given accept type is Json
    - Path param "id" value is 1
    - When user sends request to  https://jsonplaceholder.typicode.com/posts/{id}
    - Then status code is 200
    -And json body contains "repellat provident"
    - And header Content - Type is Json
    - And header "X-Powered-By" value is "Express"
    - And header "X-Ratelimit-Limit" value is 1000
    - And header "Age" value is more than 100
    - And header "NEL" value contains "success_fraction"
     */

    @Test
    public void test_2(){

        given().accept(ContentType.JSON)
                .when().get(url + "/1");

        Response response = given().log().all().accept(ContentType.JSON)
                .and().pathParam("id" , 1)
                .when().get(url + "/{id}");

        assertEquals(200, response.statusCode());
        assertTrue(response.asString().contains("repellat provident"));
        assertTrue(response.contentType().contains(ContentType.JSON.toString()));
        assertEquals("Express", response.header("X-Powered-By"));
        assertEquals("1000", response.header("X-Ratelimit-Limit"));
        assertTrue( Integer.parseInt(response.getHeader("age")) > 0);
        assertTrue(response.header("NEL").contains("success_fraction"));

    }

    /*
    - Given accept type is Json
    - Path param "id" value is 12345
    - When user sends request to  https://jsonplaceholder.typicode.com/posts/{id}
    - Then status code is 404
    - And json body contains "{}"
     */
    @Test
    public void test_3(){

        int id = 12345;

        Response response = given().accept(ContentType.JSON)
                .when().get(url + "/" + id);

        assertEquals(404, response.statusCode());
        assertTrue(response.asString().contains("{}"));

    }

    /*
    - Given accept type is Json
    - Path param "id" value is 2
    - When user sends request to  https://jsonplaceholder.typicode.com/posts/{id}/comments
    - Then status code is 200
    - And header Content - Type is Json
    - And json body contains "Presley.Mueller@myrl.com",  "Dallas@ole.me" , "Mallory_Kunze@marie.org"
     */
    @Test
    public void test_4(){

        int id = 2;

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", id)
                .when().get(url + "/{id}" + "/comments");

        assertEquals(200, response.statusCode());
        assertTrue(response.contentType().contains(ContentType.JSON.toString()));
        assertTrue(response.asString().contains("Presley.Mueller@myrl.com")
                         && response.asString().contains("Dallas@ole.me")
                         && response.asString().contains("Mallory_Kunze@marie.org"));

    }

    /*
    - Given accept type is Json
    - Query Param "postId" value is 1
    - When user sends request to  https://jsonplaceholder.typicode.com/comments
    - Then status code is 200
    - And header Content - Type is Json
    - And header "Connection" value is "keep-alive"
    - And json body contains "Lew@alysha.tv"
     */

    @Test
    public void test_5(){

        String url = "https://jsonplaceholder.typicode.com/comments";

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("postId", 1)
                .when().get(url);

        assertEquals(200, response.statusCode());
        assertTrue(response.contentType().contains(ContentType.JSON.toString()));
        assertEquals("keep-alive", response.header("Connection"));
        response.prettyPrint();
        assertTrue(response.asString().contains("Lew@alysha.tv"));

    }


    /*
    - Given accept type is Json
    - Query Param "postId" value is 333
    - When user sends request to  https://jsonplaceholder.typicode.com/comments
    - And header Content - Type is Json
    - And header "Content-Length" value is 2
    - And json body contains "[]"
     */

    @Test
    public void test_6(){

        String url = "https://jsonplaceholder.typicode.com/comments";

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("postId", 333)
                .when().get(url);

        assertTrue(response.contentType().contains(ContentType.JSON.toString()));
        assertEquals("2", response.header("Content-Length"));
        assertTrue(response.asString().contains("[]"));

    }

}
