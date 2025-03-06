package com.epam.aurora.cucumberserver.util.interaction.recorder.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.epam.aurora.cucumberserver.util.interaction.recorder.MethodCallRecorder;

public class ProxyInteractionRecorderHandlerImpl implements InvocationHandler {
    private static final Object EMPTY_RESULT = null;
    private MethodCallRecorder methodCallRecorder;

    public ProxyInteractionRecorderHandlerImpl(MethodCallRecorder methodCallRecorder) {
        this.methodCallRecorder = methodCallRecorder;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        methodCallRecorder.recordMethodCall(method, args);
        return EMPTY_RESULT;
    }
}
