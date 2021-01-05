package com.example.util;

import com.example.entity.ExcelDataVO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Author: Dreamer-1
 * Date: 2019-03-01
 * Time: 11:09
 * Description: 生成Excel并写入数据
 */
public class ExcelWriter {

    public static List<String> MASTER_CELL_HEADS = new ArrayList<>(); //系统清单表头
    public static List<String> DETAIL_CELL_HEADS = new ArrayList<>(); //数据表明细表

    static {
        // 类装载时就载入指定好的列头信息，如有需要，可以考虑做成动态生成的列头
        MASTER_CELL_HEADS.add("序号");
        MASTER_CELL_HEADS.add("系统名称及代码");
        MASTER_CELL_HEADS.add("数据库用户名称");
        MASTER_CELL_HEADS.add("表级大类（如公共类、联机类、批量类、财务类）");
        MASTER_CELL_HEADS.add("表级类别（如主表、明细表、登记簿、流水表）");
        MASTER_CELL_HEADS.add("英文表名");
        MASTER_CELL_HEADS.add("中文表名");
        MASTER_CELL_HEADS.add("数据表说明");
        MASTER_CELL_HEADS.add("主键");
        MASTER_CELL_HEADS.add("所在表空间");
        MASTER_CELL_HEADS.add("表记录长度");
        MASTER_CELL_HEADS.add("数据量估算");
        MASTER_CELL_HEADS.add("数据增加速度");
        MASTER_CELL_HEADS.add("预计存储空间");
        MASTER_CELL_HEADS.add("数据来源");
        MASTER_CELL_HEADS.add("数据常用周期");
        MASTER_CELL_HEADS.add("数据保留期限");
        MASTER_CELL_HEADS.add("数据清理备份方式");
        MASTER_CELL_HEADS.add("操作频率");
        MASTER_CELL_HEADS.add("当前状态分为：无变化、新增、修改、删除、引用（并标出引用出处）");
        MASTER_CELL_HEADS.add("DAC数据说明");
        /**
         * 表明细头
         */
        DETAIL_CELL_HEADS.add("字段序号");
        DETAIL_CELL_HEADS.add("表英文名");
        DETAIL_CELL_HEADS.add("表中文名");
        DETAIL_CELL_HEADS.add("字段英文名");
        DETAIL_CELL_HEADS.add("字段中文名");
        DETAIL_CELL_HEADS.add("字段类型");
        DETAIL_CELL_HEADS.add("长度");
        DETAIL_CELL_HEADS.add("精度");
        DETAIL_CELL_HEADS.add("是否主键");
        DETAIL_CELL_HEADS.add("外键");
        DETAIL_CELL_HEADS.add("是否可为空（Y/N）");
        DETAIL_CELL_HEADS.add("缺省值");
        DETAIL_CELL_HEADS.add("取值范围");
        DETAIL_CELL_HEADS.add("业务取值说明");
        DETAIL_CELL_HEADS.add("当前状态");
        DETAIL_CELL_HEADS.add("备注");
        DETAIL_CELL_HEADS.add("企业级数据字典数据项编号");
        DETAIL_CELL_HEADS.add("企业级数据字典匹配结果");
    }

    /**
     * 生成Excel并写入数据信息
     *
     * @param dataList 数据列表
     * @return 写入数据后的工作簿对象
     */
//    public static Workbook exportData(List<ExcelDataVO> dataList) {
//        // 生成xlsx的Excel
//        Workbook workbook = new SXSSFWorkbook();
//
//        // 如需生成xls的Excel，请使用下面的工作簿对象，注意后续输出时文件后缀名也需更改为xls
//        //Workbook workbook = new HSSFWorkbook();
//
//        // 生成Sheet表，写入第一行的列头
//        Sheet sheet = buildDataSheet(workbook);
//        //构建每行的数据内容
//        int rowNum = 1;
//        for (Iterator<ExcelDataVO> it = dataList.iterator(); it.hasNext(); ) {
//            ExcelDataVO data = it.next();
//            if (data == null) {
//                continue;
//            }
//            //输出行数据
//            Row row = sheet.createRow(rowNum++);
//            convertDataToRow(data, row);
//        }
//        return workbook;
//    }

    /**
     * 生成sheet表，并写入第一行数据（列头）
     *
     * @param workbook 工作簿对象
     * @return 已经写入列头的Sheet
     */
    public static Sheet buildDataSheet(Workbook workbook, List<String> headList, String sheetName) {
        Sheet sheet = workbook.createSheet(sheetName);
        // 设置列头宽度
        for (int i = 0; i < headList.size(); i++) {
            sheet.setColumnWidth(i, 4000);
        }
        // 设置默认行高
        sheet.setDefaultRowHeight((short) 400);
        // 构建头单元格样式
        CellStyle cellStyle = buildHeadCellStyle(sheet.getWorkbook());
        // 写入第一行各列的数据
        Row head = sheet.createRow(0);
        for (int i = 0; i < headList.size(); i++) {
            Cell cell = head.createCell(i);
            cell.setCellValue(headList.get(i));
            cell.setCellStyle(cellStyle);
        }
        return sheet;
    }

    /**
     * 设置第一行列头的样式
     *
     * @param workbook 工作簿对象
     * @return 单元格样式对象
     */
    public static CellStyle buildHeadCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        //对齐方式设置
        style.setAlignment(HorizontalAlignment.CENTER);
        //边框颜色和宽度设置
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex()); // 下边框
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex()); // 左边框
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex()); // 右边框
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex()); // 上边框
        //设置背景颜色
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //粗体字设置
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    /**
     * 设置第一行列头的样式
     *
     * @param workbook 工作簿对象
     * @return 单元格样式对象
     */
    public static CellStyle buildBodyCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        //对齐方式设置
        style.setAlignment(HorizontalAlignment.CENTER);
        //边框颜色和宽度设置
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex()); // 下边框
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex()); // 左边框
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex()); // 右边框
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex()); // 上边框
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);
        return style;
    }

    /**
     * 将数据转换成行
     *
     * @param data 源数据
     * @param row  行对象
     * @return
     */
    private static void convertDataToRow(ExcelDataVO data, Row row) {
//        int cellNum = 0;
//        Cell cell;
//        // 姓名
//        cell = row.createCell(cellNum++);
//        cell.setCellValue(null == data.getName() ? "" : data.getName());
//        // 年龄
//        cell = row.createCell(cellNum++);
//        if (null != data.getAge()) {
//            cell.setCellValue(data.getAge());
//        } else {
//            cell.setCellValue("");
//        }
//        // 所在城市
//        cell = row.createCell(cellNum++);
//        cell.setCellValue(null == data.getLocation() ? "" : data.getLocation());
//        // 职业
//        cell = row.createCell(cellNum++);
//        cell.setCellValue(null == data.getJob() ? "" : data.getJob());
    }
}

