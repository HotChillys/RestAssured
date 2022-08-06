package com.cydeo.tests.day04_path_jsonpath;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class SpartansJasonPathMethod {

    String url = "http://3.93.242.50:8000";
    @Test
    public void test_1(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParams("id", 10)
                .when().get(url + "/api/spartans/{id}");

        assertEquals(200, response.statusCode());

        assertEquals(ContentType.JSON.toString(), response.contentType());

        System.out.println( response.body().path("id").toString());
        System.out.println( response.body().path("name").toString());
        System.out.println( response.body().path("gender").toString());
        System.out.println( response.path("phone").toString());

        int id = response.body().path("id");
        String name = response.path("name");
        String gender = response.path("gender");
        long phone = response.path("phone");

        assertEquals(10, id);
        assertEquals("Lorenza", name);
        assertEquals("Female", gender);
        assertEquals(3312820936L, phone);
    }


    @Test
    public void test_2(){

        Response response = get(url + "/api/spartans");

        // extract first id
        int firstId = response.path("id[0]");
        System.out.println("first id = " + firstId);

        //extract name
        String firstFirstname = response.path("name[1]");
        System.out.println("First first name = " + firstFirstname);

        //get hte last first name
        String lastFirstname = response.path("name[-1]");
        System.out.println("Last firstname = " + lastFirstname);

        //extract all first names nad print them
        List<String> names = response.path("name");
        System.out.println(names);
        System.out.println("names = " + names.size() );

        List<Object> phoneNumbers = response.path("phone");
        for (Object eachNumber : phoneNumbers) {
            System.out.println(eachNumber);
        }


    }

}
