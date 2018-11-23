package com.hzgc.utils;

import com.hzgc.manage.enums.ResultCodeEnums;
import com.hzgc.manage.vo.ResultVO;

/**
 * 结果集封装
 * created by liang on 18-11-20
 */
public class ResultUtils {
    public static ResultVO success(Object data){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultCodeEnums.OK.getCode());
        resultVO.setData(data);
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code , String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setMsg(msg);
        resultVO.setCode(code);
        return resultVO;
    }

    public static ResultVO error(ResultCodeEnums resultCodeEnums) {
        ResultVO resultVO = new ResultVO();
        resultVO.setMsg(resultCodeEnums.getMsg());
        resultVO.setCode(resultCodeEnums.getCode());
        return resultVO;
    }
}
