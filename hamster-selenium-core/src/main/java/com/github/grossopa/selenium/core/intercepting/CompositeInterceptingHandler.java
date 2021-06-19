package com.github.grossopa.selenium.core.intercepting;

import java.util.List;

public class CompositeInterceptingHandler implements InterceptingHandler{
    private final List<InterceptingHandler> handlerList;

    public CompositeInterceptingHandler(List<InterceptingHandler> handlerList) {
        this.handlerList = handlerList;
    }

    @Override
    public void onBefore(MethodInfo<?> methodInfo) {
        handlerList.forEach(handler -> handler.onBefore(methodInfo));
    }

    @Override
    public void onAfter(MethodInfo<?> methodInfo, Object resultValue) {
        handlerList.forEach(handler -> handler.onAfter(methodInfo, resultValue));
    }

    @Override
    public void onException(MethodInfo<?> methodInfo, Exception exception) {
        handlerList.forEach(handler -> handler.onException(methodInfo, exception));
    }
}
