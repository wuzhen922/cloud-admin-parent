package com.eyelake.cloud.common.utils;

import com.eyelake.cloud.common.constants.CommonConst;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

/**
 * Excel文件工具类
 */
public class ExcelUtil {

    private final static String CHAR_SET = "UTF-8";

    private static final Logger LOG = LoggerFactory.getLogger(ExcelUtil.class);

    public static void main(String[] args) {
        System.out.println(readExcel("E:/2014年10月15日-待转账订单列表.xlsx"));
    }

    public static void exportExcelFile(HttpServletResponse response, String[] headers, List<LinkedHashMap<String, String>> contentsList, String fileName) {
        Workbook workbook = createWorkbook(headers, contentsList);
        outExcelFile(response, fileName, workbook);
    }

    /**
     * 写入XLSX文件
     * @param headers
     * @param contentsList
     * @param fileName
     */

    public static void writeXLSXFile(String[] headers, List<LinkedHashMap<String, Object>> contentsList, String fileName) {
        LOG.debug("Enter exportXLSXFile(Linked).fileName=" + fileName);

        Workbook workbook = ExcelUtil.createWorkbookContainRemark(headers,contentsList);
        // 将XLSX以文件流的形式输出
        ExcelUtil.outXLSXFile(fileName,workbook);
        LOG.debug("Exit exportCSVFile(Linked)");
    }



    /**
     * 导出Excel文件
     *
     * @param response
     * @param fileName
     * @param workbook
     */
    private static void outExcelFile(HttpServletResponse response, String fileName, Workbook workbook) {
        ServletOutputStream outStream = null;
        try {
            fileName = new String(fileName.getBytes("GB2312"), "iso-8859-1") + ".xlsx";
            response.reset();
            response.setContentType("application/download;charset=" + CHAR_SET);
            response.setCharacterEncoding(CHAR_SET);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            outStream = response.getOutputStream();
            workbook.write(outStream);
            outStream.flush();
        } catch (Exception e) {
            LOG.info("download excel failed." + e.toString());
        } finally {
            if (null != outStream) {
                try {
                    outStream.close();
                } catch (Exception e) {
                    LOG.info("Close excel outStream failed." + e.toString());
                }
            }
        }

    }

    /**
     * 创建工作表
     *
     * @param contentsList
     * @param headers
     */
    private static Workbook createWorkbook(String[] headers, List<LinkedHashMap<String, String>> contentsList) {
        XSSFWorkbook xwb = new XSSFWorkbook();
        XSSFSheet sheet = xwb.createSheet("sheet1");
        XSSFRow titleRow = sheet.createRow(0);
        // 创建title信息
        for (int i = 0; i < headers.length; i++) {
            XSSFCell xc = titleRow.createCell(i);
            xc.setCellValue(headers[i]);
        }

        // 表内容
        for (int j = 1; j <= contentsList.size(); j++) {
            LinkedHashMap<String, String> content = contentsList.get(j - 1);
            XSSFRow xssfRow = sheet.createRow(j);
            int k = 0;
            for (Object value : content.values()) {
                Object[] keyArr = content.keySet().toArray();
                String key = keyArr[k].toString();
                XSSFCell xc = xssfRow.createCell(k);
                setCellVal(xc, key, value);
                k++;
            }
        }
        return xwb;
    }

    /**
     * 将xlsx以文件的形式输出
     * @param fileName
     * @param workbook
     * @return
     */

    private static boolean outXLSXFile(String fileName, Workbook workbook) {

        FileOutputStream outStream = null;

        Properties pro = System.getProperties();

        try {

            fileName = new String(fileName.getBytes(Charset.defaultCharset()), pro.getProperty("file.encoding")) + ".xlsx";

            File file = new File(fileName);

            String parentPath = file.getParent();

            if (StringUtils.isBlank(parentPath)) {

                LOG.error("outXLSXFile.filePath " + parentPath + "is not exist.");
                return false;
            }

            LOG.debug("outXLSXFile.fileName=" + fileName);

            outStream = new FileOutputStream(file);
            workbook.write(outStream);
            outStream.flush();
        } catch (Exception e) {
            LOG.error("download xlsx failed." + e.toString());
        } finally {
            if (null != outStream) {
                try {
                    outStream.close();
                } catch (Exception e) {
                    LOG.error("Close xlsx outStream failed." + e.toString());
                }
            }
        }
        return  true;
    }


    /**
     * 设置单元格类型
     *
     * @param xc
     * @param key
     * @param value
     */
    private static void setCellVal(XSSFCell xc, String key, Object value) {
        if (value instanceof BigDecimal || key.toUpperCase().indexOf("TYPE_NUM") > 1) {
            xc.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
            xc.setCellValue(Double.valueOf(String.valueOf(value)));
        } else {
            // 此处可以扩展各种类型
            xc.setCellType(XSSFCell.CELL_TYPE_STRING);
            xc.setCellValue(String.valueOf(value));
        }
    }

    /**
     * 读取Excel文件
     *
     * @param filePath
     * @return
     */
    public static List<LinkedHashMap<String, Object>> readExcel(String filePath) {
        if (StringUtils.isBlank(filePath) || filePath.length() > 300) {
            LOG.info("filePath is invalid");
            return null;
        }
        FileInputStream input = null;
        try {
            input = new FileInputStream(new File(filePath));
        } catch (FileNotFoundException e) {
            LOG.info("file not found,filePath:{}", filePath);
            return null;
        } finally {
        }
        return readExcel(input);
    }

    /**
     * Excel文件读取，默认读取第一页
     *
     * @param inputStream
     * @return
     */
    public static List<LinkedHashMap<String, Object>> readExcel(InputStream inputStream) {
        return readExcel(inputStream, 0);
    }

    /**
     * Excel文件读取
     * @param inputStream
     * @return
     */
    public static List<LinkedHashMap<String, Object>> readExcel(InputStream inputStream, int sheetIndex) {
        // 读取文件的数据结构，按行读取
        List<LinkedHashMap<String, Object>> data = new ArrayList<LinkedHashMap<String, Object>>();
        //按照导入模板进行导入，则应该在第六行开始读取
        int rowNumber = CommonConst.CLOUD_IMPORT_READ_ROW_NUMBER;

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(new BufferedInputStream(inputStream));
            XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
            if (null == sheet) {
                LOG.debug("导入的表为空，请确认，文件名：{}", inputStream);
                return data;
            }


            XSSFRow headerRow = sheet.getRow(sheet.getFirstRowNum() + rowNumber);
            int colNum = headerRow.getLastCellNum();

            String[] headers = new String[colNum];
            for (int r = headerRow.getFirstCellNum(); r < headerRow.getLastCellNum(); r++) {
                Cell cell = headerRow.getCell(r);
                headers[r] = getCellVal(cell).toString();
            }

            for (int i = sheet.getFirstRowNum() + 1 + rowNumber; i <= sheet.getLastRowNum(); i++) {
                XSSFRow xssfRow = sheet.getRow(i);
                if(null == xssfRow){
                    continue;
                }
                LinkedHashMap<String, Object> rowData = new LinkedHashMap<String, Object>();
                for (int j = xssfRow.getFirstCellNum(); j < xssfRow.getLastCellNum(); j++) {
                    Cell cell = xssfRow.getCell(j);
                    if (null != cell){
                        Object value = getCellVal(cell);
                        if (null != value && value != ""){
                            rowData.put(headers[j], value);
                        }
                    }
                }

                if (null == rowData || rowData.size() < 1){
                    continue;
                }

                data.add(rowData);

            }

        } catch (Exception e) {
            LOG.error("transfer data import exception,error:{}", e);
            return null;
        }
        return data;
    }


    /**
     * 读取Excel单元格数据
     *
     * @param cell
     * @return
     */
    private static Object getCellVal(Cell cell) {
        Object object = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                object = "";
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                object = cell.getBooleanCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                    object = cell.getDateCellValue();
                } else {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    object = cell.getStringCellValue().trim();
                }
                break;
            case Cell.CELL_TYPE_STRING:
                object = cell.getStringCellValue().trim();
                break;
            case Cell.CELL_TYPE_ERROR:
                object = "";
                break;
            case Cell.CELL_TYPE_FORMULA:
                cell.setCellType(Cell.CELL_TYPE_STRING);
                String temp = cell.getStringCellValue();
                if (null != temp) {
                    temp = temp.replaceAll("#N/A", "").trim();
                }
                object = temp;
                break;
        }
        return object;
    }

    /**
     *
     * @param workbook
     * @param fontsize
     * @return 单元格样式
     */
    private static XSSFCellStyle createCellStyle(Workbook workbook, short fontsize, boolean flag, boolean flag1) {

        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        //是否水平居中
        if(flag1){
            style.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
        }

        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
        //创建字体
        XSSFFont font = (XSSFFont) workbook.createFont();
        //是否加粗字体
        if(flag){
            font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        }
        font.setFontHeightInPoints(fontsize);
        //加载字体
        style.setFont(font);
        return style;
    }

    /**
     * @param contentsList
     * @param headers
     *
     */
    private static Workbook createWorkbookContainRemark(String[] headers, List<LinkedHashMap<String, Object>> contentsList) {
        XSSFWorkbook xwb = new XSSFWorkbook();

        XSSFSheet sheet = xwb.createSheet("sheet1");
        sheet.addMergedRegion(new CellRangeAddress(0,4,0,8));
        XSSFCellStyle headStyle = createCellStyle(xwb,(short)10,false,true);
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        cell.setCellStyle(headStyle);
        cell.setCellValue("备注：集成商入网许可号选填，运营商编号选填，运营商编号00：中国移动；01：中国联通；03：中国电信，其他必填；RTU传输类型，只能填4G、NB-IoT、北斗和天通； RTU接入类型，只能填有通道或无通道");

        XSSFRow titleRow = sheet.createRow(CommonConst.CLOUD_IMPORT_READ_ROW_NUMBER);
        // 创建title信息
        for (int i = 0; i < headers.length; i++) {
            XSSFCell xc = titleRow.createCell(i);

            xc.setCellValue(headers[i]);
        }

        // 表内容
        for (int j = 1; j <= contentsList.size(); j++) {
            LinkedHashMap<String, Object> content = contentsList.get(j - 1);
            XSSFRow xssfRow = sheet.createRow(j + CommonConst.CLOUD_IMPORT_READ_ROW_NUMBER);
            int k = 0;
            for (Object value : content.values()) {
                Object[] keyArr = content.keySet().toArray();
                String key = keyArr[k].toString();
                XSSFCell xc = xssfRow.createCell(k);
             //   xc.setCellType(XSSFCell.CELL_TYPE_STRING);
             //   xc.setCellValue(String.valueOf(value));
                setCellVal(xc, key, value);
                k++;
            }
        }
        return xwb;
    }
}
