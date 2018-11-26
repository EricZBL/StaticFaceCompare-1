package com.hzgc.manage.handler;

import com.hzgc.exception.HzgcException;
import com.hzgc.manage.enums.ExceptionCodeEnums;
import com.hzgc.manage.enums.ResultCodeEnums;
import com.hzgc.manage.vo.ResultVO;
import com.hzgc.utils.ResultUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * 异常统一处理类
 * created by liang on 18-11-20
 */
@ControllerAdvice
@ResponseBody
public class AuthorizeExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResultVO exceptionHandler(Exception e) {

        // 参数校验异常
        if (e instanceof MethodArgumentNotValidException) {

            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;

            List<ObjectError> errors = ex.getBindingResult().getAllErrors();
            ObjectError error= errors.get(0);
            ResultVO resultVO = ResultUtils.error(ExceptionCodeEnums.PARAM_ERROR.getCode(), error.getDefaultMessage());
            return resultVO;

         // 自定义异常
        }else if (e instanceof HzgcException) {
            HzgcException ex = (HzgcException) e;
            return ResultUtils.error(ex.getCode() , ex.getMessage());
        }else if (e instanceof NoSuchElementException){

            return ResultUtils.success();
        }
        return ResultUtils.error(ResultCodeEnums.ERROR);
    }
}


