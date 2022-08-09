package com.cydeo.tests.day05_path_jsonpath;

import com.cydeo.util.ConfigurationReader;
import com.cydeo.util.HRApiTestBase;
import com.cydeo.util.SpartanTestBase;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class HREmployeesJsonpathTest extends HRApiTestBase {

    /**
     Given accept type is Json
     And query param limit is 200
     when I send GET request to /employees
     Then I can use jsonPath to query and read values from json body
     */


    @DisplayName("GET /employees?limit=200 => jasonPath filters")
    @Test
    public void test_1(){

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("limit", 200)
                .when().get("/employees");

        assertEquals(HttpStatus.SC_OK, response.statusCode());

        JsonPath jsonPath = response.jsonPath();

        System.out.println("first emp firstname = " + jsonPath.getString("items[0].first_name"));
        System.out.println("first emp job id = " + jsonPath.getString("items[0].job_id"));

        //Working with JsonPath lists:
        //get all the emails into a list and print out
        List<String> emails = jsonPath.getList("items.email");
        System.out.println("emails = " + emails);
        //assert TGATES is among emails:
        assertTrue(emails.contains("TGATES"));

        //get all employee first names who work for department_id 90
        List<String> namesAtDept90 = jsonPath.getList("items.findAll{it.department_id==90}.first_name");
        System.out.println("namesAtDept90 = " + namesAtDept90);
        System.out.println("namesAtDept90.size() = " + namesAtDept90.size());

        System.out.println("===> " + jsonPath.getList("items.first_name").size());

        System.out.println("IT Programmers ==> " + jsonPath.getList("items.findAll{it.job_id=='IT_PROG'}.first_name"));

        System.out.println("salary >= 5000 ==> " + jsonPath.getList("items.findAll{it.salary >= 5000}.first_name"));

        System.out.println("max salary ==> " + jsonPath.getString("items.max{it.salary}.first_name"));

        System.out.println("min salary ==> " + jsonPath.getString("items.min{it.salary}.first_name"));
    }


}
