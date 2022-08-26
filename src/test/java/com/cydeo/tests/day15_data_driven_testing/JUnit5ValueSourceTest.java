package com.cydeo.tests.day15_data_driven_testing;

import com.cydeo.util.ConfigurationReader;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class JUnit5ValueSourceTest {

    @ParameterizedTest
    @ValueSource(ints = {5, 23, 90, 33, 64, 10986, 456})
    public void numberTest(int num) {
        System.out.println("num = " + num);
        assertThat(num, is(greaterThan(0)));
    }


    @ParameterizedTest
    @ValueSource(strings = {"Saim", "Nadir", "James", "Tony", "Tina", "Kevin"})
    public void testNames(String name){
        System.out.println("Hi! " + name);
        assertThat(name, not(blankOrNullString()));
    }

    @BeforeAll
    public static void setUp(){
        baseURI = ConfigurationReader.getProperty("zipcode.api.url");
    }

    @ParameterizedTest
    @ValueSource(ints = {22102, 22031, 22034, 11209, 15090, 15237,12345,20879,21224,33433})
    public void zipcodeTest(int zipcode){

       given().accept(ContentType.JSON)
               .and().pathParam("zipcode", zipcode)
               .when().get("/us/{zipcode}").prettyPeek()
               .then().statusCode(200);

    }

}
