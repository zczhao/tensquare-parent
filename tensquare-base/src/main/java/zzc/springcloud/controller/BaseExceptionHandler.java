package zzc.springcloud.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import zzc.springcloud.entity.Result;
import zzc.springcloud.entity.StatusCode;

/**
 * 统一异常处理类
 *
 */
@RestControllerAdvice
public class BaseExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public Result error(Exception e) {
		e.printStackTrace();
		return new Result(false, StatusCode.ERROR, e.getMessage());
	}
}
