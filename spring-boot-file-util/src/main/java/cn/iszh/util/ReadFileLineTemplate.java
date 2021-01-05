package cn.iszh.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 读取上传文件接口模板
 */
public class ReadFileLineTemplate {
    public static void handel(MultipartFile multipartFile, CallBack readLineCallBack) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = multipartFile.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                readLineCallBack.handle(line);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }
    }

    public interface CallBack {
        void handle(String line);
    }
}


