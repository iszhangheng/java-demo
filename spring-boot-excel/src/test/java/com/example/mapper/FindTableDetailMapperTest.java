package com.example.mapper;

import com.example.entity.ExcelDataVO;
import com.example.util.ExcelReader;
import com.example.util.ExcelWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FindTableDetailMapperTest {

    @Resource
    FindTableDetailMapper findTableDetailMapper;

    @Test
    public void printExcel() {
        String[] dbNames = new String[]{"dobank_app", "dobank_job", "dobank_manage", "dobank_msg", "dobank_merchant"};
        for (String dbName : dbNames) {
            test(dbName);
        }
    }

    public void test(String dbName) {
        List<String> readResult = ExcelReader.readExcel("C:\\Users\\zhangheng\\Desktop\\配置管理数据库表设计说明书.xlsx");
        assert readResult != null;
        Set<String> collect = new HashSet<>(readResult);
        // 生成xlsx的Excel
        Workbook workbook = new SXSSFWorkbook();
        // 构建普通单元格样式
        CellStyle cellStyle = ExcelWriter.buildBodyCellStyle(workbook);
        List<Map<String, Object>> tableList = findTableDetailMapper.selectAllTableByDbName(dbName);
        Set<String> sheetName = new HashSet<>();
        for (Map<String, Object> item : tableList) {
            if (collect.contains(String.valueOf(item.get("表英文名")).toUpperCase())) {
                System.err.println(String.valueOf(item.get("表英文名")).toUpperCase() + "已存在");
                continue;
            }
            Sheet sheet = ExcelWriter.buildDataSheet(workbook, ExcelWriter.DETAIL_CELL_HEADS, getSheetName(item, sheetName));
            List<Map<String, Object>> columnList = findTableDetailMapper.selectByDbNameAndTableName(dbName, String.valueOf(item.get("表英文名")));
            int j = 1;
            for (Map<String, Object> column : columnList) {
                Row row = sheet.createRow(j++);
                for (int h = 0; h < ExcelWriter.DETAIL_CELL_HEADS.size(); h++) {
                    Cell cell = row.createCell(h);
                    parseCellValue(cell, column.get(ExcelWriter.DETAIL_CELL_HEADS.get(h)));
                    cell.setCellStyle(cellStyle);
                }
            }
        }
        // 以文件的形式输出工作簿对象
        FileOutputStream fileOut = null;
        try {
            String exportFilePath = "C:\\Users\\zhangheng\\Desktop\\" + dbName + ".xlsx";
            File exportFile = new File(exportFilePath);
            if (!exportFile.exists()) {
                exportFile.createNewFile();
            }

            fileOut = new FileOutputStream(exportFilePath);
            workbook.write(fileOut);
            fileOut.flush();
        } catch (Exception e) {
            System.err.println("输出Excel时发生错误，错误原因：" + e.getMessage());
        } finally {
            try {
                if (null != fileOut) {
                    fileOut.close();
                }
                if (null != workbook) {
                    workbook.close();
                }
            } catch (IOException e) {
                System.err.println("关闭输出流时发生错误，错误原因：" + e.getMessage());
            }
        }
    }

    public static String getSheetName(Map<String, Object> item, Set<String> sheetNames) {
        String sheetEnName = String.valueOf(item.get("表英文名"));
        String sheetCnName = String.valueOf(item.get("表中文名"));
        String sheetName = sheetCnName.equals("") ? sheetEnName : sheetCnName;
        if (sheetNames.contains(sheetName)) {
            System.err.println(sheetName + "重复" + sheetEnName);
            if (sheetEnName.equals(sheetName)) {
                return "Test" + System.currentTimeMillis();
            }
            return sheetEnName;
        }
        sheetNames.add(sheetName);
        return sheetName;
    }

    public static void parseCellValue(Cell cell, Object value) {
        if (value instanceof Number) {
            cell.setCellValue(Double.parseDouble(String.valueOf(value)));
            return;
        }
        if (value instanceof String) {
            cell.setCellValue(String.valueOf(value).toUpperCase());
            return;
        }
        if (value == null) {
            cell.setCellValue("");
        }
    }

}