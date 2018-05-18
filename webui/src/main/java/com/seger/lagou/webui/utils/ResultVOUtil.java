package com.seger.lagou.webui.utils;

import com.seger.lagou.webui.vo.ResultVO;

/**
 * 结果结构化工具
 *
 * @author: seger.lin
 */

public class ResultVOUtil {
    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        return resultVO;
    }
}
