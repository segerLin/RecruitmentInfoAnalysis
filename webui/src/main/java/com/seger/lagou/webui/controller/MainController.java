package com.seger.lagou.webui.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: seger.lin
 */

@RestController
@RequestMapping("/")
public class MainController {
    @GetMapping("/index")
    public ModelAndView list(){
        return new ModelAndView("index");
    }

}
