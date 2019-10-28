package cn.iszh;

import java.util.Properties;

/**
 * @version: V1.0
 * @author: Mr.Zhang
 * @className: FileUtil
 * @packageName: cn.iszh
 * @description: 文件操作工具类
 * @date: 2019-09-29 9:32
 **/
public class FileSystemUtil {
    static final Properties PROPERTIES = new Properties(System.getProperties());

    /**
     * 获取当前系统文件路径分隔符
     *
     * @return
     */
    public static String getSystemPathSeparator() {
        return PROPERTIES.getProperty("file.separator");
    }

    public static void main(String[] args) {
        System.out.println(getSystemPathSeparator());
    }
}
