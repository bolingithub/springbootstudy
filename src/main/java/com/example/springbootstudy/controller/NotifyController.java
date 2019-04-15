package com.example.springbootstudy.controller;

import com.example.springbootstudy.controller.dto.DTOFactory;
import com.example.springbootstudy.controller.dto.NotifyDTO;
import com.example.springbootstudy.controller.dto.ServiceResult;
import com.example.springbootstudy.database.entity.Notify;
import com.example.springbootstudy.database.repository.NotifyRepository;
import com.example.springbootstudy.error.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.example.springbootstudy.controller.dto.ServiceResult.SUCCESS;

@RestController
@RequestMapping("notify")
public class NotifyController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    NotifyRepository notifyRepository;

    @GetMapping("getSysNotify")
    ServiceResult<List<NotifyDTO>> getSysNotify(@RequestParam String userId, Integer offset, Integer limit) throws ServiceException {
        if (offset == null) {
            offset = 0;
        }
        if (limit == null) {
            limit = 20;
        }
        logger.debug("请求系统通知 offset：" + offset + " limit：" + limit);
        List<Notify> notifyList = notifyRepository.findByUserIdAndTypeAndStatus(userId, 0, 0, limit, offset);
        List<NotifyDTO> notifyDTOList = new ArrayList<>();
        for (Notify notify : notifyList) {
            notifyDTOList.add(DTOFactory.notify2DTO(notify));
        }
        return new ServiceResult<>(0, SUCCESS, notifyDTOList);
    }

    @GetMapping("delSysNotify")
    ServiceResult<Void> delSysNotify(@RequestParam String userId, @RequestParam Integer notifyId) {
        Notify notify = notifyRepository.findByIdAndUserId(notifyId, userId);
        if (notify != null) {
            notify.setStatus(2);
            notifyRepository.save(notify);
        }
        return new ServiceResult<>(0, SUCCESS, null);
    }
}
