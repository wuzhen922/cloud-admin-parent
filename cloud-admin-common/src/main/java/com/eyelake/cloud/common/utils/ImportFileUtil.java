/**
 *
 */
package com.eyelake.cloud.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 导入文件工具类
 *
 */
public class ImportFileUtil {

    private static final Logger logger = LoggerFactory.getLogger(ImportFileUtil.class);

    /**
     * @param in
     */
    public static boolean saveFile(InputStream in, String desFilePath) {
        try {
            File file = new File(desFilePath);
            if (!file.exists())
                file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            int c;
            byte buffer[] = new byte[1024];
            while ((c = in.read(buffer)) != -1) {
                for (int i = 0; i < c; i++)
                    out.write(buffer[i]);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            logger.info("FileNotFoundException,file path:{},exception:{}", desFilePath, e.toString());
            return false;
        } catch (IOException e) {
            logger.info("IOException,{}", e.toString());
            return false;
        } catch (Exception e) {
            logger.info("Exception,{}", e);
            return false;
        }
        return true;
    }

}
