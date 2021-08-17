package com.qf.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/desktopController")
@Slf4j
public class DesktopController {
    @RequestMapping("/getDesktopPage")
    public String getDesktopPage(){
        return  "desktop/desktopPage";
    }
}
