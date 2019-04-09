package com.example.springbootstudy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("helloSpringBoot")
public class HelloSpringBootController {

    private static final Logger logger = LoggerFactory.getLogger(HelloSpringBootController.class);

    @GetMapping("printLog")
    public String printLog() {
        logger.info("info -- 用户请求打印日志");
        logger.error("error -- 用户请求打印日志");
        return "print log success";
    }

    @PostMapping("appError")
    public String appError(@RequestParam String name) throws Exception {
        logger.info("请求参数 -- name：" + name);
        logger.error("抛出异常");
        throw new Exception("自定义异常");
    }
}
