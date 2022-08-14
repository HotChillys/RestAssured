package com.cydeo.tests.day06_xmlpath_deserialization;

import com.cydeo.util.SpartanTestBase;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class SpartanXmlPathTest extends SpartanTestBase {

    /**
     Given accept type is application/xml
     When i send GET request to /api/spartans
     Then status code is 200
     And content type is XML
     And spartan at index 0 is matching:
     id > 107
     name>Ezio Auditore
     gender >Male
     phone >7224120202

     */

    @DisplayName("GET /spartans -> sml path")
    @Test
    public void spartanXMLPathTest(){
        Response response = given().accept(ContentType.XML)
                .when().get("spartans");



        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.XML.toString(), response.contentType());

        XmlPath xmlPath = response.xmlPath();

        System.out.println("id = " + xmlPath.getInt("List.item[0].id"));
        assertEquals(107, xmlPath.getInt("List.item[0].id"));

        System.out.println("name = " + xmlPath.getString("List.item[0].name"));
        assertEquals("Ezio Auditore", xmlPath.getString("List.item[0].name"));

        System.out.println("gender = " + xmlPath.getString("List.item[0].gender"));
        assertEquals("Male", xmlPath.getString("List.item[0].gender"));

        System.out.println("phone = " + xmlPath.getLong("List.item[0].phone"));
        assertEquals(7224120202L, xmlPath.getLong("List.item[0].phone"));



    }

}
