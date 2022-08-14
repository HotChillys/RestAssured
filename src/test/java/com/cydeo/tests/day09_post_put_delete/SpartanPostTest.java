package com.cydeo.tests.day09_post_put_delete;

import com.cydeo.pojo.Spartan;
import com.cydeo.util.SpartanTestBase;
import com.cydeo.util.SpartansRestUtil;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class SpartanPostTest extends SpartanTestBase {

    /**
     given accept is json and content type is json
     and body is:
     {
     "gender": "Male",
     "name": "TestPost1",
     "phone": 1234567425
     }
     When I send POST request to /spartans
     Then status code is 201
     And content type is json
     And "success" is "A Spartan is Born!"
     Data name, gender , phone matches my request details
     */

    @DisplayName("POST new spartan using string json")
    @Test
    public void addNewSpartansJsonTest() {

        String jsonBody = " {\n" +
                "     \"gender\": \"Male\",\n" +
                "     \"name\": \"TestingTesting\",\n" +
                "     \"phone\": 1234567425\n" +
                "     }";

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(jsonBody)
                .when().post("/spartans");
        response.prettyPrint();

        //verify status code
        System.out.println("status code = " + response.statusCode());
        assertThat(response.statusCode(), is(201));

        //verify header
        assertThat(response.contentType(), is("application/json"));

        //assign response to json path
        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getString("success"), equalTo("A Spartan is Born!"));
        assertThat(jsonPath.getString("data.name"), is("TestingTesting"));
        assertThat(jsonPath.getString("data.gender"), is("Male"));
        assertThat(jsonPath.getLong("data.phone"), is(1234567425L));

        //extract the id of newly added spartan
        int spartanId = jsonPath.getInt("data.id");
        System.out.println("spartanId = " + spartanId);

        //delete the spartan after post
        SpartansRestUtil.deleteSpartanById(spartanId);

    }

    @DisplayName("POST /spartans using map - serialization")
    @Test
    public void assNewSpartanAsMapTest() {

        Map<String, Object> spartanPostMap = new HashMap<>();
            spartanPostMap.put("gender", "Male");
            spartanPostMap.put("name", "testYou");
            spartanPostMap.put("phone", 1234556789L);

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(spartanPostMap)   //Map to json - serialization
                .when().post("/spartans");
        response.prettyPrint();

        //verify status code
        System.out.println("status code = " + response.statusCode());
        assertThat(response.statusCode(), is(201));

        //verify header
        assertThat(response.contentType(), is("application/json"));

        //assign response to json path
        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getString("success"), equalTo("A Spartan is Born!"));
        assertThat(jsonPath.getString("data.name"), is(spartanPostMap.get("name")));
        assertThat(jsonPath.getString("data.gender"), is(spartanPostMap.get("gender")));
        assertThat(jsonPath.getLong("data.phone"), is(spartanPostMap.get("phone")));

        //extract the id of newly added spartan
        int spartanId = jsonPath.getInt("data.id");
        System.out.println("spartanId = " + spartanId);

        //delete the spartan after post
        SpartansRestUtil.deleteSpartanById(spartanId);

    }


    @DisplayName("POST /spartans using POJO - serilization")
    @Test
    public void addNewSpartanAsPojoTest() {

        Spartan newSpartan = new Spartan();
        newSpartan.setName("Male");
        newSpartan.setGender("TestYou");
        newSpartan.setPhone(1234567425L);




    }

}
