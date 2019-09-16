package com.woowacourse.tecobrary.common.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class IndexController {

    @GetMapping
    public ResponseEntity index() {
        return ResponseEntity.ok("Hello. This is Tecobrary API v1. Welcome !");
    }
}
