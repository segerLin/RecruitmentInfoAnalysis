package com.seger.lagou.webui.controller;

import com.seger.lagou.webui.dataobject.JobNameProvince;
import com.seger.lagou.webui.dataobject.JobNameProvinceCity;
import com.seger.lagou.webui.service.JobNameProvinceCityService;
import com.seger.lagou.webui.service.JobNameProvinceService;
import com.seger.lagou.webui.utils.ResultVOUtil;
import com.seger.lagou.webui.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping("/hot")
public class HotJobController {

    @Autowired
    JobNameProvinceService provinceService;

    @Autowired
    JobNameProvinceCityService cityService;

    @GetMapping("/list")
    public ModelAndView list(){
        return new ModelAndView("hotjob/hotjob");
    }

    @GetMapping("/province")
    public ResultVO province(@RequestParam("province") String province){
        PageRequest request = new PageRequest( 0, 10);
        List<JobNameProvince> list = new ArrayList<JobNameProvince>();
        Page<JobNameProvince> page = provinceService.findByJobProvince(province,request);
        for(JobNameProvince item : page){
            list.add(item);
        }
        return ResultVOUtil.success(list);
    }

    @GetMapping("/city")
    public ResultVO city(@RequestParam("province") String province,@RequestParam("city") String city){
        PageRequest request = new PageRequest( 0, 10);
        List<JobNameProvinceCity> list = new ArrayList<JobNameProvinceCity>();
        Page<JobNameProvinceCity> page = cityService.findByJobProvinceAndJobCity(province, city, request);
        for(JobNameProvinceCity item : page){
            list.add(item);
        }
        return ResultVOUtil.success(list);
    }


    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("title") String title){
        Map<String,String> map= new HashMap<String, String>();
        map.put("title", title);
        return new ModelAndView("hotjob/detail", map);
    }

    @GetMapping("/jobList")
    public ModelAndView jobList(Map<String, Object> map){
        String str = ".NET\n" +
                "Android\n" +
                "APP设计师\n" +
                "ASP\n" +
                "C\n" +
                "C#\n" +
                "C++\n" +
                "CDN\n" +
                "CEO\n" +
                "CFO\n" +
                "COCOS2D-X\n" +
                "CTO\n" +
                "DB2\n" +
                "Delphi\n" +
                "ETL\n" +
                "F5系统管理员\n" +
                "Flash设计师\n" +
                "Go\n" +
                "Hadoop\n" +
                "Hive\n" +
                "HRBP\n" +
                "HTML5\n" +
                "IDC\n" +
                "iOS\n" +
                "IT支持\n" +
                "Java\n" +
                "JavaScript\n" +
                "MongoDB\n" +
                "MySQL\n" +
                "NLP\n" +
                "Node.js\n" +
                "Oracle\n" +
                "Perl\n" +
                "PHP\n" +
                "Python\n" +
                "Ruby\n" +
                "Shell\n" +
                "Spark\n" +
                "SQLServer\n" +
                "U3D\n" +
                "UI设计师\n" +
                "unreal\n" +
                "VB\n" +
                "WEB安全\n" +
                "WP\n" +
                "交互设计师\n" +
                "产品经理\n" +
                "产品运营\n" +
                "人力资源\n" +
                "人工智能\n" +
                "全栈工程师\n" +
                "内容运营\n" +
                "前端\n" +
                "区块链\n" +
                "原画师\n" +
                "后端开发\n" +
                "商家运营\n" +
                "图像处理\n" +
                "图像识别\n" +
                "培训经理\n" +
                "多媒体设计师\n" +
                "大数据开发\n" +
                "安全专家\n" +
                "小程序\n" +
                "平面设计师美术设计师（2D\n" +
                "广告设计师\n" +
                "技术合伙人\n" +
                "技术总监\n" +
                "技术经理\n" +
                "招聘\n" +
                "搜索算法\n" +
                "数据仓库\n" +
                "数据分析\n" +
                "数据分析师\n" +
                "数据挖掘\n" +
                "数据科学\n" +
                "数据运营\n" +
                "新媒体运营\n" +
                "无线交互设计师\n" +
                "机器学习\n" +
                "机器视觉\n" +
                "架构师\n" +
                "活动运营\n" +
                "测试总监\n" +
                "海外运营\n" +
                "深度学习\n" +
                "游戏制作\n" +
                "游戏动作\n" +
                "游戏场景\n" +
                "游戏数值策划\n" +
                "游戏特效\n" +
                "游戏界面设计师\n" +
                "游戏角色\n" +
                "用户研究员\n" +
                "用户研究总监\n" +
                "用户运营\n" +
                "病毒分析\n" +
                "硬件交互设计师\n" +
                "移动开发\n" +
                "算法工程师\n" +
                "精准推荐\n" +
                "系统安全\n" +
                "系统工程师\n" +
                "网店运营\n" +
                "网络安全\n" +
                "网络工程师\n" +
                "网络推广\n" +
                "网页交互设计师\n" +
                "网页设计师\n" +
                "自然语言处理\n" +
                "行政总监\n" +
                "视觉设计师\n" +
                "视频算法\n" +
                "语音识别\n" +
                "财务总监\n" +
                "运维工程师\n" +
                "运维开发工程师\n" +
                "运维总监\n" +
                "运维经理\n" +
                "运营专员\n" +
                "运营经理\n" +
                "项目助理\n" +
                "项目总监\n" +
                "项目经理";
        List<String> jobList = new ArrayList<String>(Arrays.asList(str.split("\n")));
        map.put("data", jobList);
        map.put("dataLen", jobList.size());
        return new ModelAndView("hotjob/list", map);
    }
}
