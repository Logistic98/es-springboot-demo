package com.es.demo.utils;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 数据下发拦截器(统一接口响应格式的组件）
 */

@RestControllerAdvice
public class FormatResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        boolean isResponseBeanType = ResponseBean.class.equals(returnType.getParameterType());
        // 如果返回的是 ResponseBean 类型，则无需进行拦截修改，直接返回即可
        // 其他类型则拦截，并进行 beforeBodyWrite 方法进行修改
        return !isResponseBeanType;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return ResponseBean.success(body);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBean<String> handleException() {
        return ResponseBean.failure("Error occured");
    }
}
