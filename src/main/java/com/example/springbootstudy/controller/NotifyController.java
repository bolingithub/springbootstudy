package com.example.springbootstudy.controller;

import com.example.springbootstudy.controller.dto.ServiceResult;
import com.example.springbootstudy.database.entity.Notify;
import com.example.springbootstudy.database.repository.NotifyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.springbootstudy.controller.dto.ServiceResult.SUCCESS;

@RestController
@RequestMapping("notify")
public class NotifyController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    NotifyRepository notifyRepository;

    @GetMapping("getSysNotify")
    ServiceResult<List<Notify>> getSysNotify(@RequestParam String userId, Integer offset, Integer limit) {
        if (offset == null) {
            offset = 0;
        }
        if (limit == null) {
            limit = 20;
        }
        logger.debug("请求系统通知 offset：" + offset + " limit：" + limit);
        List<Notify> notifyList = notifyRepository.findByUserIdAndTypeAndStatus(userId, 0, 0, limit, offset);
        return new ServiceResult<>(0, SUCCESS, notifyList);
    }
}
