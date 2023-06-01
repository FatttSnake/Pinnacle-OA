package com.cfive.pinnacle.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class ExceptionController {
    @RequestMapping("/thrown")
    public void thrown(HttpServletRequest request) {
        throw (RuntimeException) request.getAttribute("filter.error");
    }
}
