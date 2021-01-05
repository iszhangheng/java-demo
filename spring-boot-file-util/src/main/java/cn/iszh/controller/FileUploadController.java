package cn.iszh.controller;

import cn.iszh.util.ReadFileLineTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile multipartFile) {
        final StringBuilder result = new StringBuilder();
        ReadFileLineTemplate.handel(multipartFile, new ReadFileLineTemplate.CallBack() {
            public void handle(String line) {
                result.append(line);
            }
        });
        return result.toString();
    }


}
