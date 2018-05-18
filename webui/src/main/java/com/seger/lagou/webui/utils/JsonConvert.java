package com.seger.lagou.webui.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.*;

/**
 * @author: seger.lin
 */

public class JsonConvert {


    public static void listHotJob(){
        String path = "//Users/seger/Documents/PDF/lagou_res/jobData/jobExperienceDstri";
        File file = new File(path);
        try{
            if(file.exists()){
                String[] files= file.list();
                for(String f :files){
                    System.out.println(f);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void changeBatch(){
        String path = "//Users/seger/Documents/PDF/lagou_res/jobData/jobSalaryAboutCity";
        File file = new File(path);
        ObjectMapper mapper = new ObjectMapper();

        try{
            if(file.exists()){
                String[] ds= file.list();
                for(String d :ds){
                    File subDir = new File(path + "/" + d);
                    if(subDir.exists() && subDir.isDirectory()){
                        String[] files = subDir.list();
                        List<Object> jsonItem = new ArrayList<Object>();
                        for(String f: files){
                            if(f.endsWith(".json")){
                                File jsonFile = new File(path + "/" + d + "/" + f);
                                if (jsonFile.length() > 0 && jsonFile.isFile()){
                                    Map<String, Object> map = mapper.readValue(
                                            jsonFile,
                                            new TypeReference<Map<String, Object>>() {
                                            });
                                    jsonItem.add(map);
                                }
                                jsonFile.delete();
                            }
                        }
                        Map<String,Object> writeItem = new HashMap<String,Object>();
                        writeItem.put("data", jsonItem);
                        mapper.writeValue(new File(path + "/" + d + "/"+ d +".json"),  writeItem);
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void readBatch(){
        String path = "//Users/seger/Documents/PDF/lagou_res/jobData/jobExperience";
        File file = new File(path);
        ObjectMapper mapper = new ObjectMapper();

        try{
            if(file.exists()){
                String[] files= file.list();
                for(String f :files){
                    if(!f.endsWith(".json"))
                        continue;
                    Map<String, Object> map = mapper.readValue(
                            new File(path + "/" +f),
                            new TypeReference<Map<String, Object>>() {
                            });
                    String jsonString = mapper.writeValueAsString(map);
                    jsonString = jsonString.replaceAll("key","name");

                    map = mapper.readValue(jsonString,  new TypeReference<Map<String, Object>>() {
                    });
                    for(String key:map.keySet()){
                        Map<String, Object> writeMap = new HashMap<String, Object>();
                        writeMap.put("data",map.get(key));
                        mapper.writeValue(new File(path + "/new/" + key + ".json"),  writeMap);
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void convertHotCompany(){
        String path = "/Users/seger/Documents/PDF/lagou_res/jobCompany";
        String fileName = "jobCompanySize.json";
        String nameList = "北京三快在线科技有限公司\n" +
                "北京字节跳动科技有限公司\n" +
                "百度在线网络技术（北京）有限公司\n" +
                "北京嘀嘀无限科技发展有限公司\n" +
                "腾讯科技(深圳)有限公司\n" +
                "网易（杭州）有限公司\n" +
                "北京骏嘉财通科技有限公司\n" +
                "北京麦田房产经纪有限公司\n" +
                "上海泛微网络科技股份有限公司\n" +
                "北京世纪好未来教育科技有限公司\n" +
                "北京尚德在线教育科技有限公司\n" +
                "普信恒业科技发展（北京）有限公司\n" +
                "武汉佰钧成技术有限责任公司\n" +
                "北京五八到家信息技术有限公司\n" +
                "北京京东世纪贸易有限公司\n" +
                "北京爱奇艺科技有限公司\n" +
                "北京小米科技有限责任公司\n" +
                "陕西巨丰投资资讯有限责任公司北京分公司\n" +
                "支付宝(杭州)信息技术有限公司\n" +
                "奇虎360科技有限公司\n" +
                "京东金融\n" +
                "拉扎斯网络科技（上海）有限公司\n" +
                "北京通力互联技术服务有限公司\n" +
                "上海游族信息技术有限公司\n" +
                "携程计算机技术（上海）有限公司\n" +
                "乐视网信息技术（北京）股份有限公司\n" +
                "北京搜狐新媒体信息技术有限公司\n" +
                "北京车之家信息技术有限公司\n" +
                "联想（北京）有限公司\n" +
                "北京数美时代科技有限公司\n" +
                "北京国双科技有限公司\n" +
                "阿里巴巴（中国）网络技术有限公司\n" +
                "北京趣拿软件科技有限公司\n" +
                "广州华多网络科技有限公司\n" +
                "上海彩亿信息技术有限公司\n" +
                "厦门美图之家科技有限公司\n" +
                "北京搜狗科技发展有限公司\n" +
                "上海我图网络科技有限公司\n" +
                "北京小度信息科技有限公司\n" +
                "杭州大搜车汽车服务有限公司\n" +
                "北京善义善美科技有限公司\n" +
                "北京蜜莱坞网络科技有限公司\n" +
                "达内时代科技集团有限公司\n" +
                "上海海万信息科技股份有限公司\n" +
                "拉勾测试润石创投\n" +
                "金色家园网络科技有限公司\n" +
                "上海幻电信息科技有限公司\n" +
                "上海秦苍信息科技有限公司\n" +
                "广州酷旅旅行社有限公司\n" +
                "平安科技（深圳）有限公司\n" +
                "北京云杉世界信息技术有限公司\n" +
                "深圳市金证科技股份有限公司\n" +
                "北京陌陌科技有限公司\n" +
                "深圳依时货拉拉科技有限公司\n" +
                "北京斗米优聘科技发展有限公司\n" +
                "用友网络科技股份有限公司\n" +
                "银客未来科技（北京）有限公司\n" +
                "优视科技有限公司\n" +
                "拓天伟业（北京）金融信息服务有限公司\n" +
                "链家网（北京）科技有限公司\n" +
                "北京值得买科技股份有限公司\n" +
                "人人行科技有限公司\n" +
                "深圳市乐易网络有限公司\n" +
                "众安在线财产保险股份有限公司\n" +
                "资质管家（北京）信息技术有限公司\n" +
                "上海微创软件股份有限公司北京分公司\n" +
                "北京闪银奇异科技有限公司\n" +
                "北京盈泰财富云电子商务有限公司\n" +
                "唯品会（中国）有限公司\n" +
                "合一网络技术（北京）有限公司\n" +
                "炫一下（北京）科技有限公司\n" +
                "北京世纪超星信息技术发展有限责任公司\n" +
                "上海七牛信息技术有限公司\n" +
                "华为技术有限公司\n" +
                "深圳市大疆创新科技有限公司\n" +
                "深圳英迈思文化科技有限公司\n" +
                "网宿科技股份有限公司北京分公司\n" +
                "上海链家房地产经纪有限公司\n" +
                "北京汇通天下物联科技有限公司\n" +
                "观澜网络（杭州）有限公司\n" +
                "车好多旧机动车经纪(北京)有限公司\n" +
                "北京融联世纪信息技术有限公司\n" +
                "三七互娱（上海）科技有限公司\n" +
                "行吟信息科技（上海）有限公司\n" +
                "广州博冠信息科技有限公司\n" +
                "上海一起作业信息科技有限公司\n" +
                "点我达网络科技有限公司\n" +
                "上海寻梦信息技术有限公司\n" +
                "北京互动百科网络技术股份有限公司\n" +
                "上海青客公共租赁住房租赁经营管理股份有限公司\n" +
                "浙江执御信息技术有限公司\n" +
                "武汉斗鱼网络科技有限公司\n" +
                "杭州爱上租科技有限公司\n" +
                "杭州施强网络科技有限公司\n" +
                "北京作业盒子科技有限公司\n" +
                "北京京东尚科信息技术有限公司\n" +
                "东峡大通（北京）管理咨询有限公司\n" +
                "上海容易网电子商务股份有限公司\n" +
                "上海钧正网络科技有限公司\n" +
                "玖富金科控股集团有限责任公司";
//        List<String> keyList = Arrays.asList(nameList.split("\n"));
        try{
            ObjectMapper mapper = new ObjectMapper();

            // read JSON from a file
            Map<String, Object> map = mapper.readValue(
                    new File(path + "/" +fileName),
                    new TypeReference<Map<String, Object>>() {
                    });

            for(String key:map.keySet()){
//                for(String str : keyList){
//                    if(key.equals(str)){
                Map<String, Object> writeMap = new HashMap<String, Object>();
                writeMap.put("data",map.get(key));
                mapper.writeValue(new File(path + "/size/" + key + ".json"),  writeMap);
//                break;
//                    }
//                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        listHotJob();
    }
}
