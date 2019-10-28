package cn.iszh;

import cn.iszh.file.FileTools;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @version: V1.0
 * @author: Mr.Zhang
 * @className: FileUtil
 * @packageName: cn.iszh
 * @description: 文件创建修改删除工具类
 * @date: 2019-09-29 9:42
 **/
public class FileUtil implements FileTools {
    private File file;
    private FileOutputStream fileOutputStream;
    private BufferedWriter bufferedWriter;

    public static void main(String[] args) {
        String[] strList = {"1", "2", "3", "4", "5", "6"};
        FileTools fileTools = new FileUtil();
        if (fileTools.writeList(fileTools.currentPath(), "test.log", Arrays.asList(strList))) {
            System.out.println("SUCCESS");
        } else {
            System.out.println("FAIL");
        }
        fileTools.close();
    }

    @Override
    public String currentSystemSeparator() {
        return File.separator;
    }

    @Override
    public String currentPath() {
        return System.getProperty("user.dir");
    }

    @Override
    public boolean writeList(String path, String fileName, List list) {
        Objects.requireNonNull(path, "写入文件时路径不能为空");
        Objects.requireNonNull(fileName, "写入文件时文件名不能为空");
        try {
            file = new File(path + currentSystemSeparator() + fileName);
            fileOutputStream = new FileOutputStream(file, true);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"));
            for (Object s : list) {
                bufferedWriter.write(s.toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void close() {
        if (bufferedWriter != null) {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
