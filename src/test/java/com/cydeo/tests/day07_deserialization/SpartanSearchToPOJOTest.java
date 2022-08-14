package com.cydeo.tests.day07_deserialization;

import com.cydeo.pojo.Spartan;
import com.cydeo.pojo.SpartansSearch;
import com.cydeo.util.SpartanTestBase;
import org.apache.http.protocol.HTTP;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.restassured.response.Response;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


public class SpartanSearchToPOJOTest extends SpartanTestBase {

    /**
     * Given accept type is json
     * And query param nameContains value is "e"
     * And query param gender value is "Female"
     * When I send get request to /spartans/search
     * Then status code is 200
     * And content type is Json
     * And json response data is as expected
     */

    @Test
    public void spartanSearchToPojoTest() {

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("nameContains", "e");
        queryMap.put("gender", "Female");

        Response response = given().accept(ContentType.JSON)
                .and().queryParams(queryMap)
                .when().get("/spartans/search");

//        response.prettyPrint();

        assertEquals(200, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());

        //deserialize Json to SpartanSearch pojo class
        SpartansSearch spartansSearch = response.as(SpartansSearch.class);

        // total elemnet
        System.out.println("total Element = " + spartansSearch.getTotalElement());
        System.out.println("All spartans = " + spartansSearch.getContent());
        System.out.println("First spartan info = " + spartansSearch.getContent().get(0));

        //goto to content list of spartans and get index 1 single spartan
        Spartan secondSpartan = spartansSearch.getContent().get(1);
        System.out.println("secondSpartan = " + secondSpartan);
        System.out.println("second spartan name = " + secondSpartan.getName());
        System.out.println("second spartan id = " + secondSpartan.getId());

        List<Spartan> spartanData = spartansSearch.getContent();
        System.out.println("second spartan = " + spartanData.get(1));

        //read all names into a list
        List<String> names = new ArrayList<>();
        for (Spartan sp : spartanData) {
            names.add(sp.getName());
        }

        System.out.println("names = " + names);
        //using functional programming. stream
        List<String> allNames = spartanData.stream().map(sp -> sp.getName()).collect(Collectors.toList());
        System.out.println("allNames = " + allNames);
    }



}


