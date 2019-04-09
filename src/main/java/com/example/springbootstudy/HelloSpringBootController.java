package com.example.springbootstudy;

import com.example.springbootstudy.database.UserRepository;
import com.example.springbootstudy.database.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("helloSpringBoot")
public class HelloSpringBootController {

    private static final Logger logger = LoggerFactory.getLogger(HelloSpringBootController.class);

    @Autowired
    private UserRepository userRepository;

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

    @GetMapping("saveUser")
    public String saveUser(@RequestParam String phone, @RequestParam String nickname) {
        Users users = new Users();
        users.setPhone(phone);
        users.setNickname(nickname);
        userRepository.save(users);
        return "Success";
    }

    @GetMapping("getUsersByNickname")
    public List<Users> getUsersByNickname(@RequestParam String nickname) {
        return userRepository.findByNickname(nickname);
    }
}
