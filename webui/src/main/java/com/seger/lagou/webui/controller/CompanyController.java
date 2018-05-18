package com.seger.lagou.webui.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: seger.lin
 */

@RestController
@RequestMapping("/company")
public class CompanyController {

    @GetMapping("/area")
    public ModelAndView byArea(){
        return new ModelAndView("company/area");
    }

    @GetMapping("/area/wordCloud")
    public ModelAndView areaWordCloud(@RequestParam("area") String area){
        Map<String,String>  map = new HashMap<String, String>();
        map.put("type", "area");
        map.put("key", area);
        return new ModelAndView("company/wordCloud", map);
    }

    @GetMapping("/name")
    public ModelAndView byName(){
        return new ModelAndView("company/name");
    }

    @GetMapping("/name/wordCloud")
    public ModelAndView nameWordCloud(@RequestParam("name") String name){
        Map<String,String>  map = new HashMap<String, String>();
        map.put("type", "name");
        map.put("key", name);
        return new ModelAndView("company/wordCloud", map);
    }

    @GetMapping("/size")
    public ModelAndView bySize(){
        return new ModelAndView("company/size");

    }

    @GetMapping("/size/wordCloud")
    public ModelAndView sizeWordCloud(@RequestParam("size") String size){
        Map<String,String>  map = new HashMap<String, String>();
        map.put("type", "size");
        map.put("key", size);
        return new ModelAndView("company/wordCloud", map);
    }



    @GetMapping("/nop")
    public ModelAndView byNop(){
        return new ModelAndView("company/nop");
    }


    @GetMapping("/nop/wordCloud")
    public ModelAndView nopWordCloud(@RequestParam("nop") String nop){
        Map<String,String>  map = new HashMap<String, String>();
        map.put("type", "nop");
        map.put("key", nop);
        return new ModelAndView("company/wordCloud",map);
    }

    @GetMapping("/test")
    public ModelAndView test(){
        return new ModelAndView("company/test");
    }

}
