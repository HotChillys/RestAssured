package com.cydeo.tests.day16_methodsource_mocks;

import com.cydeo.util.BookItAPITestBase;
import com.cydeo.util.ExcelUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;

public class BookItAPIMethodSourceExcelTest extends BookItAPITestBase {

    @DisplayName("GET /sign -> data from BookItQa3.xlsx")
    @ParameterizedTest
    @MethodSource("getUserCredentials")
    public void bookItAuthDDTTest(Map<String, String> userInfo){

        System.out.println("user info = " + userInfo); // can be use as QueryParams in API call

    }





    public static List<Map<String, String>> getUserCredentials() {

        String filePath = "src/test/resources/BookItQa3.xlsx";
        ExcelUtil excelReader = new ExcelUtil(filePath, "QA3");
        List<Map<String, String>> data = excelReader.getDataList();

        return data;
    }

}
