package com.jsdc.ybpt.controller.notice;

import com.jsdc.ybpt.service.notice.NoticeScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/noticeScope")
public class NoticeScopeController {
    @Autowired
    private NoticeScopeService service;
}
