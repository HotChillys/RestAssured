package com.cydeo.tests.day16_methodsource_mocks;

import com.cydeo.util.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class MethodSourceTest {

    /**
     * data provider method
     * @return
     */
    public static List<String> getCountries() {

        List<String> countries = Arrays.asList("Canada", "USA", "France", "Turkey", "Mexico", "Egypt", "Germany");

        return countries;
    }


    @ParameterizedTest
    @MethodSource("getCountries")
    public void countriesTest(String countryName) {

        System.out.println(countryName + " " + countryName.length());

    }



}
