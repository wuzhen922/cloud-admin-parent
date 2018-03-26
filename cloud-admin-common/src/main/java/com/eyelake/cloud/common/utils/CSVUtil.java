package com.eyelake.cloud.common.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 生成并导出CSV文件
 */
public class CSVUtil {

    private final static String DELIMITER = ",";

    private final static String LINE_END = "\r\n";

    private final static String COLONO = "\"";

    private final static String CHAR_SET = "UTF-8";

    private final static String TAB = "\t";

    private static Logger logger = Logger.getLogger(CSVUtil.class);

    /**
     * 导出CSV文件
     *
     * @param response
     * @param headers
     * @param contentsList
     * @param fileName     [参数说明]
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void exportCSVFile(HttpServletResponse response, String[] headers,
                                     List<LinkedHashMap<String, String>> contentsList, String fileName) {
        logger.debug("Enter exportCSVFile(Linked).fileName=" + fileName);
        CSVUtil csvUtil = new CSVUtil();
        // 获取csv内容
        String csvContent = csvUtil.createCSVContent(headers, contentsList);
        // 将csv以文件流的形式输出
        csvUtil.outCVSFile(response, fileName, csvContent);
        logger.debug("Exit exportCSVFile(Linked)");
    }

    /**
     * 导出CSV文件
     *
     * @param response
     * @param headers
     * @param contentsList
     * @param fileName     [参数说明]
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void exportCSVFile(HttpServletResponse response, String[] headers, String[] keys,
                                     List<Map<String, String>> contentsList, String fileName) {
        logger.debug("Enter exportCSVFile.fileName=" + fileName);
        CSVUtil csvUtil = new CSVUtil();
        // 获取csv内容
        String csvContent = csvUtil.createCSVContent(headers, keys, contentsList);
        // 将csv以文件流的形式输出
        csvUtil.outCVSFile(response, fileName, csvContent);
        logger.debug("Exit exportCSVFile");
    }

    /**
     * 将csv以文件流的形式输出
     *
     * @param response
     * @param fileName
     * @param csvContent [参数说明]
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private void outCVSFile(HttpServletResponse response, String fileName, String csvContent) {
        ServletOutputStream outStream = null;
        try {
            fileName = new String(fileName.getBytes("GB2312"), "iso-8859-1") + ".csv";
            logger.debug("outCVSFile.fileName=" + fileName);
            response.reset();
            response.setContentType("application/download;charset=" + CHAR_SET);
            response.setCharacterEncoding(CHAR_SET);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            // response.setContentType("application/csv;charset=utf-8");
            byte[] datas = csvContent.getBytes("GB2312");
            // byte[] datas = csvContent.getBytes();
            outStream = response.getOutputStream();
            outStream.write(datas, 0, datas.length);
            outStream.flush();
        } catch (Exception e) {
            logger.error("download csv failed." + e.toString());
        } finally {
            if (null != outStream) {
                try {
                    outStream.close();
                } catch (Exception e) {
                    logger.error("Close csv outStream failed." + e.toString());
                }
            }
        }
    }

    /**
     * 生成CSV格式的内容
     *
     * @param headers
     * @param contentsList
     * @return StringBuffer [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String createCSVContent(String[] headers, String[] keys, List<Map<String, String>> contentsList) {
        StringBuffer content = new StringBuffer();
        if (null != headers && headers.length > 0) {
            StringBuffer cols = new StringBuffer();
            for (int i = 0; i < headers.length; i++) {
                cols.append(getContent(headers[i])).append(DELIMITER);
            }
            content.append(cols.toString().substring(0, cols.toString().length() - 1));
            content.append(LINE_END);
        }

        if (null == contentsList || contentsList.isEmpty()) {
            return content.toString();
        }

        for (Map<String, String> rowMap : contentsList) {
            if (null == rowMap || rowMap.isEmpty()) {
                continue;
            }

            StringBuffer cols = new StringBuffer();
            for (int i = 0; i < keys.length; i++) {
                String col = rowMap.get(keys[i]);
                cols = cols.append(getContent(col)).append(DELIMITER);
            }
            content.append(cols.toString().substring(0, cols.toString().length() - 1));
            content.append(LINE_END);
        }
        return content.toString();
    }

    /**
     * 生成CSV格式的内容
     *
     * @param headers
     * @param contentsList
     * @return StringBuffer [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String createCSVContent(String[] headers, List<LinkedHashMap<String, String>> contentsList) {
        StringBuffer content = new StringBuffer();
        if (null != headers && headers.length > 0) {
            StringBuffer cols = new StringBuffer();
            for (int i = 0; i < headers.length; i++) {
                cols.append(getContent(headers[i])).append(DELIMITER);
            }
            content.append(cols.toString().substring(0, cols.toString().length() - 1));
            content.append(LINE_END);
        }

        if (null == contentsList || contentsList.isEmpty()) {
            return content.toString();
        }

        for (LinkedHashMap<String, String> rowMap : contentsList) {
            if (null == rowMap || rowMap.isEmpty()) {
                continue;
            }

            StringBuffer cols = new StringBuffer();
            for (Object colObj : rowMap.values()) {
                String colstr = "";
                if (null != colObj) {
                    colstr = getContent(String.valueOf(colObj));
                }
                cols = cols.append(colstr).append(DELIMITER);
            }
            content.append(cols.toString().substring(0, cols.toString().length() - 1));
            content.append(LINE_END);
        }

        return content.toString();
    }

    /**
     * 对内容进行转义
     *
     * @param str
     * @return String [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private String getContent(String str) {
        String c = "";
        if (null != str) {
            c = str.replace(COLONO, COLONO + COLONO);
            // 解决数字会转化为科学计数法的问题，且数字过长（超过11位），科学计数法会显示不全
            // if (-1 != c.indexOf(DELIMITER))
            // {
            Pattern p = Pattern.compile("^[0-9\\.,]*$");
            Matcher m = p.matcher(c);
            if (m.matches() && c.length() <= 11) {
                c = COLONO.concat(c).concat(COLONO);
            } else {
                c = COLONO.concat(TAB).concat(c).concat(TAB).concat(COLONO);
            }

            // c =
            // "="+COLONO.concat(c).concat(COLONO);//这种该法会导致鼠标点击出现等号，且金钱有逗号是分列会有问题。
            // }

        }
        return c;
    }

    private File createCSVFile(List exportData, LinkedHashMap rowMapper, String outPutPath, String filename) {
        File csvFile = null;
        BufferedWriter csvFileOutputStream = null;
        try {
            csvFile = new File(outPutPath + filename + ".csv");
            // csvFile.getParentFile().mkdir();
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            // GB2312使正确读取分隔符","
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), CHAR_SET),
                    1024);
            // 写入文件头部
            for (Iterator propertyIterator = rowMapper.entrySet().iterator(); propertyIterator.hasNext(); ) {
                Map.Entry propertyEntry = (Map.Entry) propertyIterator.next();
                csvFileOutputStream.write("\"" + propertyEntry.getValue().toString() + "\"");
                if (propertyIterator.hasNext()) {
                    csvFileOutputStream.write(",");
                }
            }
            csvFileOutputStream.newLine();
            // 写入文件内容
            for (Iterator iterator = exportData.iterator(); iterator.hasNext(); ) {
                Object row = (Object) iterator.next();
                for (Iterator propertyIterator = rowMapper.entrySet().iterator(); propertyIterator.hasNext(); ) {
                    Map.Entry propertyEntry = (Map.Entry) propertyIterator.next();
                    csvFileOutputStream.write("\""
                            + BeanUtils.getProperty(row, propertyEntry.getKey().toString()).toString() + "\"");
                    if (propertyIterator.hasNext()) {
                        csvFileOutputStream.write(",");
                    }
                }
                if (iterator.hasNext()) {
                    csvFileOutputStream.newLine();
                }
            }
            csvFileOutputStream.flush();
        } catch (Exception e) {
            logger.error("Exception", e);
        } finally {
            try {
                if (null != csvFileOutputStream) {
                    csvFileOutputStream.close();
                }
            } catch (IOException e) {
                logger.error("IOException", e);
            }
        }
        return csvFile;
    }

}
