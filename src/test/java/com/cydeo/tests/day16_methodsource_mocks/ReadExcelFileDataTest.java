package com.cydeo.tests.day16_methodsource_mocks;

import com.cydeo.util.ExcelUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class ReadExcelFileDataTest {

    @Test
    public void readBookItUsersTest(){

        String filePath = "src/test/resources/BookItQa3.xlsx";

        ExcelUtil excelUtil = new ExcelUtil(filePath, "QA3" );

        System.out.println("column names = " + excelUtil.getColumnsNames());

        System.out.println("row count = " + excelUtil.rowCount());

        System.out.println("cell data = " + excelUtil.getCellData(1, 1));

        List<Map<String, String>> data = excelUtil.getDataList();

        System.out.println("data = " + data);

    }


}
