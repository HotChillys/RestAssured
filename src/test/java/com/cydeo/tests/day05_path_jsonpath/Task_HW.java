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
import static org.hamcrest.Matchers.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class Task_HW extends HRApiTestBase {

    /**
     * Please use jsonPath approach to extract values from response.
     * <p>
     * ORDS API:
     * Q1:
     * - Given accept type is Json
     * - Path param value- US
     * - When users sends request to /countries
     * - Then status code is 200
     * - And Content - Type is Json
     *
     * - And country_id is US
     * - And Country_name is United States of America
     * - And Region_id is 2
     */


    @Test
    public void test_1() {
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("country", "US")
                .when().get("/countries/{country}");

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());

        JsonPath jsonPath = response.jsonPath();

        assertEquals("US", jsonPath.getString("country_id"));
        assertEquals("United States of America", jsonPath.getString("country_name"));
        assertEquals(2, jsonPath.getInt("region_id"));



    }



    /**
     * Q2:
     * - Given accept type is Json
     * - Query param value - q={"department_id":80}
     * - When users sends request to /employees
     * - Then status code is 200
     * - And Content - Type is Json
     *
     * - And all job_ids start with 'SA'
     * - And all department_ids are 80
     * - Count is 25
     */

    @Test
    public void test_2(){

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"department_id\":80}")
                .when().get("/employees");

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());

        JsonPath jsonPath = response.jsonPath();

        List<String> jobIds = jsonPath.getList("items.job_id");
        //option 1  my way
        for (String eachJobs : jobIds) {
            assertTrue(eachJobs.startsWith("SA"));
        }
        // option 2 Mehmet
        List<String> afterFilter = jobIds.stream().filter(each -> each.startsWith("SA")).collect(Collectors.toList());
        assertEquals(afterFilter.size(), jobIds.size());
        // option 3 Mehmet
        assertTrue(jobIds.stream().allMatch(each -> each.startsWith("SA")));




        // all department_id is 80
        List<Integer> departmentIds = jsonPath.getList("items.department_id");
        //option 1
        for (Integer eachId : departmentIds) {
            assertEquals(80, eachId);
        }
        //option 2 Mehmet
        assertTrue(departmentIds.stream().allMatch(each -> each == 80));



        assertEquals(25, jsonPath.getInt("count"));

    }


    /**
     * Q3:
     * - Given accept type is Json
     * -Query param value q= region_id 3
     * - When users sends request to /countries
     * - Then status code is 200
     *
     * - And all regions_id is 3
     * - And count is 6
     * - And hasMore is false
     * - And Country_name are;
     * Australia,China,India,Japan,Malaysia,Singapore
     */

    @Test
    public void test_3(){

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_id\":3}")
                .when().get("/countries")
                .then().body("items.country_name", hasItems("Australia", "China", "India", "Japan", "Malaysia", "Singapore"))
                .and().body("items.country_name", containsInRelativeOrder("Australia", "China", "India", "Japan", "Malaysia", "Singapore"))
                .extract().response();

        //Then status code is 200
        assertEquals(HttpStatus.SC_OK, response.statusCode());

        JsonPath jsonPath = response.jsonPath();

        //And all regions_id is 3
        List<Integer> allRegionId = jsonPath.getList("items.region_id");
        for (Integer eachRegionId : allRegionId) {
            assertEquals(3, eachRegionId);
        }

        // or
        assertTrue(allRegionId.stream().allMatch(each -> each == 3));

        //And count is 6
        assertEquals(6, jsonPath.getInt("count"));

        //And hasMore is false
        assertFalse(jsonPath.getBoolean("hasMore"));

        //* - And Country_name are Australia,China,India,Japan,Malaysia,Singapore
        List<String> countryList = jsonPath.getList("items.country_name");
        List<String> expectedCountryList = Arrays.asList("Australia", "China", "India", "Japan", "Malaysia", "Singapore");
        assertEquals(expectedCountryList, countryList);
        // ot we verify it with hamcrest using Matcher in the beginning




    }

}
