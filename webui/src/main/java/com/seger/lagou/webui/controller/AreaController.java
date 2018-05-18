package com.seger.lagou.webui.controller;

import com.seger.lagou.webui.dataobject.JobProvinceCity;
import com.seger.lagou.webui.dataobject.JobProvinceCityArea;
import com.seger.lagou.webui.dataobject.ProvinceCity;
import com.seger.lagou.webui.service.JobProvinceCityAreaService;
import com.seger.lagou.webui.service.JobProvinceCityService;
import com.seger.lagou.webui.service.ProvinceCityService;
import com.seger.lagou.webui.utils.ResultVOUtil;
import com.seger.lagou.webui.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * @author: seger.lin
 */


@RestController
@RequestMapping("/area")
public class AreaController {

    @Autowired
    JobProvinceCityService jobProvinceCityService;

    @Autowired
    ProvinceCityService provinceCityService;

    @Autowired
    JobProvinceCityAreaService jobProvinceCityAreaService;

    @GetMapping("/province")
    public ModelAndView province(){
        return new ModelAndView("area/province");
    }


    @GetMapping("/getCity")
    public ResultVO getCity(@RequestParam("province") String province){

        //从数据库查询相关省份-城市职位信息
        List<ProvinceCity> list = provinceCityService.findByProvince(province);
        List<String> cityList = new ArrayList<String>();
        for(ProvinceCity provinceCity : list){
            cityList.add(provinceCity.getCity());
        }
        List<JobProvinceCity> jobProvinceCities = jobProvinceCityService.findByJobProvince(province);
        for(JobProvinceCity item : jobProvinceCities){
            for(String city: cityList){
                if(city.startsWith(item.getJobCity())){
                    item.setJobCity(city);
                    break;
                }
            }
        }
        return ResultVOUtil.success(jobProvinceCities);
    }

    @GetMapping("/getArea")
    public ResultVO getArea(@RequestParam("province") String province,
                            @RequestParam("city") String city){

        Map<String,List<String>> areaMap = new HashMap<String,List<String>>();
        areaMap.put("北京",new ArrayList<String>(Arrays.asList("朝阳区 海淀区 东城区 大兴区 西城区 丰台区 昌平区 通州区 石景山区 顺义区 房山区 门头沟区 平谷区 怀柔区 密云区 延庆区".split(" "))));
        areaMap.put("重庆",new ArrayList<String>(Arrays.asList("渝北区 江北区 渝中区 南岸区 九龙坡区 沙坪坝区 大渡口区 北碚区 江津区 巴南区 永川区 万州区 涪陵区 合川区 垫江县 潼南区 梁平县 巫溪县 云阳县 大足县 开县 彭水苗族土家族自治县 荣昌区 璧山县 酉阳县 石柱土家族自治县 铜梁区 綦江区".split(" "))));
        areaMap.put("上海",new ArrayList<String>(Arrays.asList("浦东新区 徐汇区 长宁区 闵行区 黄浦区 杨浦区 普陀区 静安区 虹口区 宝山区 闸北区 嘉定区 松江区 青浦区 奉贤区 金山区 崇明县".split(" "))));
        areaMap.put("天津",new ArrayList<String>(Arrays.asList("南开区 和平区 西青区 河西区 滨海新区 河东区 东丽区 河北区 红桥区 武清区 津南区 北辰区 宝坻区 蓟县 宁河县 静海县".split(" "))));
        List<JobProvinceCityArea> dirtyData = jobProvinceCityAreaService.findByJobProvinceAndJobCity(province,city);
        List<JobProvinceCityArea> res = new ArrayList<JobProvinceCityArea>();
        for(JobProvinceCityArea item : dirtyData){
            for(String area: areaMap.get(province)){
                if(item.getJobArea().equals(area)){
                    res.add(item);
                    break;
                }
            }
        }
        return ResultVOUtil.success(res);
    }


    @GetMapping("/city")
    public ModelAndView city(@RequestParam("province") String province){

        //从数据库查询相关省份-城市职位信息
        Map<String, String> map = new HashMap<String, String>();
        map.put("province",province);
        return new ModelAndView("area/city",map);
    }


}
