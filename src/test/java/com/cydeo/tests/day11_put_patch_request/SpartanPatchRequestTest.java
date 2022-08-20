package com.cydeo.tests.day11_put_patch_request;

import com.cydeo.util.SpartanTestBase;
import com.cydeo.util.*;

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

public class SpartanPatchRequestTest extends SpartanTestBase {

    /**
     * Given accept type is json
     * And content type is json
     * And path param id is 15
     * And json body is
     * {
     * "phone": 1234567425
     * }
     * When i send PATCH request to /spartans/{id}
     * Then status code 204
     */

    @DisplayName("PATCH /api.spartans/{id}")
    @Test
    public void spartanPatchTest() {

        Map<String, Long> requestMap = new HashMap<>();
        requestMap.put("phone", 5555555555L);
        given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .pathParam("id", 23)
                .and().body(requestMap)
                .when().patch("/spartans/{id}")
                .then().statusCode(204)
                .and().body(emptyString());

        Map<String, Object> spartanMap = SpartansRestUtil.getSpartanId(23);
        System.out.println(spartanMap);

        assertThat(spartanMap.get("phone"), is(5555555555L));
        //or
        assertThat(spartanMap.get("phone"), equalTo(requestMap.get("phone")));

    }


}
