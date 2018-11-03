package com.cll.demo.example.exception;

import com.cll.demo.example.dto.Result;
import com.cll.demo.redis.exception.RedisLockException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author chenliangliang
 * @date 2018/11/3
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 拦截通用异常
     *
     * @param e 通用异常
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("拦截到异常:{}", e.getMessage(), e);
        return Result.fail("服务器异常：" + e.getMessage());
    }

    /**
     * 拦截参数异常
     *
     * @param e 参数异常
     */
    @ExceptionHandler({BindException.class})
    public Result handleBindException(BindException e) {
        return getResult(e.getBindingResult());
    }

    private Result getResult(BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        StringBuilder error = new StringBuilder();
        allErrors.forEach(it -> error.append(it.getDefaultMessage()).append(","));
        error.deleteCharAt(error.length() - 1);
        log.warn("拦截到参数异常:{}", error.toString());
        return Result.fail("参数异常：" + error.toString());
    }

    /**
     * 拦截参数异常
     *
     * @param e 参数异常
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return getResult(e.getBindingResult());
    }


    /**
     * 拦截参数异常
     *
     * @param e 参数异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn("拦截到用户消息输入异常:{}", e.getMessage(), e);
        return Result.fail("消息格式错误!");
    }



    /**
     * 拦截redisLock异常
     *
     * @param e redisLock异常
     */
    @ExceptionHandler(RedisLockException.class)
    public Result handleRedisLockException(RedisLockException e) {
        log.error("拦截到redisLock异常:{}", e.getMessage(), e);
        return Result.fail("服务器繁忙, 请稍后再试");
    }

}
