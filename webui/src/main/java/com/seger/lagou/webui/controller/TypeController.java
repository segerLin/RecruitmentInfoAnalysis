package com.seger.lagou.webui.controller;

import com.seger.lagou.webui.dataobject.JobTypeProvince;
import com.seger.lagou.webui.dataobject.JobTypeProvinceCity;
import com.seger.lagou.webui.dto.JobTypeDTO;
import com.seger.lagou.webui.service.JobTypeProvinceCityService;
import com.seger.lagou.webui.service.JobTypeProvinceService;
import com.seger.lagou.webui.utils.ResultVOUtil;
import com.seger.lagou.webui.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * 类型维度进行显示
 *
 * @author: seger.lin
 */

@RestController
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private JobTypeProvinceService jobTypeProvinceService;

    @Autowired
    private JobTypeProvinceCityService jobTypeProvinceCityService;

    @GetMapping("/list")
    public ModelAndView list(){
        return new ModelAndView("type/first");
    }

    @GetMapping("/province")
    public ResultVO province(@RequestParam("province") String province) {

        List<JobTypeProvince> list = jobTypeProvinceService.findByJobProvince(province);
        List<JobTypeDTO> dtos = new ArrayList<JobTypeDTO>();
        for(JobTypeProvince jobTypeProvince: list){
            JobTypeDTO jobTypeDTO = new JobTypeDTO();
            BeanUtils.copyProperties(jobTypeProvince, jobTypeDTO);
            dtos.add(jobTypeDTO);
        }
        return ResultVOUtil.success(dtos);
    }

    @GetMapping("/city")
    public ResultVO city(@RequestParam("province") String province,
                         @RequestParam("city") String city) {

        List<JobTypeProvinceCity> list = jobTypeProvinceCityService.findByJobProvinceAndJobCity(province,city);
        List<JobTypeDTO> dtos = new ArrayList<JobTypeDTO>();
        for(JobTypeProvinceCity jobTypeProvinceCity: list){
            JobTypeDTO jobTypeDTO = new JobTypeDTO();
            BeanUtils.copyProperties(jobTypeProvinceCity, jobTypeDTO);
            dtos.add(jobTypeDTO);
        }
        return ResultVOUtil.success(dtos);
    }




}
