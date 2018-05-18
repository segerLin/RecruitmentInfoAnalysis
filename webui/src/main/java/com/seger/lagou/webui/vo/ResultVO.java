package com.seger.lagou.webui.vo;

import lombok.Data;

/**
 * 格式化结果输出
 *
 * @author: seger.lin
 */

@Data
public class ResultVO<T> {
    private T data;
}
