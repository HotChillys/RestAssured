package com.cydeo.tests.day07_deserialization;

import com.cydeo.pojo.Spartan;
import com.cydeo.util.SpartanTestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class AllSpartansPojoTest extends SpartanTestBase {

    @Test
    public void allSpartansToPojoTest() {

        Response response = given().accept(ContentType.JSON)
                .when().get("/spartans");

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());

        // convert response to Jsonpath
        JsonPath jsonPath = response.jsonPath();

        // using jsonpath to extract List of spartans/ do deserialization
        List<Spartan> allSpartans = jsonPath.getList("", Spartan.class);
        System.out.println("count = " + allSpartans.size());

        // first spartan
        System.out.println("first spartan = " + allSpartans.get(0));

        // using stream : filter and store females spartans into a different List
        List<Spartan> allFemales = allSpartans.stream()
                .filter(spartan -> spartan.getGender().equals("Female"))
                .collect(Collectors.toList());
        System.out.println("all female size = " + allFemales.size());

        // using stream : filter and store males spartans into a different List
        List<Spartan> allMales = allSpartans.stream()
                .filter(spartan -> spartan.getGender().equals("Male"))
                .collect(Collectors.toList());
        System.out.println("all male = " + allMales.size());

        // or count
        long count = allSpartans.stream().filter(spartan -> spartan.getGender().equals("Male")).count();
        System.out.println("male count = " + count);


    }


}
