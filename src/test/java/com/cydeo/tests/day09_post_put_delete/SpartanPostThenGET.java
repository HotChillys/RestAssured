package com.cydeo.tests.day09_post_put_delete;

import com.cydeo.pojo.Spartan;
import com.cydeo.util.SpartanTestBase;
import com.cydeo.util.SpartansRestUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SpartanPostThenGET extends SpartanTestBase {

    Spartan newSpartan = SpartansRestUtil.getSpartan();

    @DisplayName("POST /spartan -> GET with id and compare")
    @Test
    public void postSpartanThenGETTest(){

        System.out.println("new spartan = " + newSpartan);

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(newSpartan)
                .when().post("/spartans");

        response.prettyPrint();
        assertThat(response.statusCode(), is(201));

        // get id value using jsonPath
        int newSpartanId = response.jsonPath().getInt("data.id");

        // send GET request with newSpartanID and compare
        Response response1 = given().accept(ContentType.JSON)
                .and().pathParam("id", newSpartanId)
                .when().get("/spartans/{id}");


        // convert GET request json body to another object. deserialization
        Spartan spartanFromGet = response1.as(Spartan.class);

        // compare Posted newSpartan with GET request spartan
        assertThat(spartanFromGet.getName(), equalTo(newSpartan.getName()));
        assertThat(spartanFromGet.getGender(), equalTo(newSpartan.getGender()));
        assertThat(spartanFromGet.getPhone(), equalTo(newSpartan.getPhone()));

        //  delete using ID at the end
        SpartansRestUtil.deleteSpartanById(newSpartanId);


    }

}
