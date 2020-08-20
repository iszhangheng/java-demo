package cn.iszhangheng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/product")
public class ProductController {

    @ResponseBody
    @RequestMapping("/info")
    public String productInfo() {
        return "Some Product Info";
    }

}
