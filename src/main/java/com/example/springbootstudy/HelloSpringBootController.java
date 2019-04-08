package com.example.springbootstudy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("helloSpringBoot")
public class HelloSpringBootController {

    private Logger logger = LoggerFactory.getLogger(HelloSpringBootController.class);

    @GetMapping("printLog")
    public String printLog() {
        logger.info("info -- 用户请求打印日志");
        logger.error("error -- 用户请求打印日志");
        return "print log success";
    }
}
