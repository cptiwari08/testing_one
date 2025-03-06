package com.epam.aurora.cucumberserver.util.interaction.recorder.impl;

import java.lang.reflect.Proxy;

import com.epam.aurora.cucumberserver.util.interaction.recorder.MethodCallRecorder;
import com.epam.aurora.cucumberserver.util.interaction.recorder.MethodCallRecordingContext;
import com.epam.aurora.cucumberserver.util.interaction.recorder.MethodCallRecordingContextFactory;

public class MethodCallRecordingContextFactoryImpl implements MethodCallRecordingContextFactory {
    @Override
    public MethodCallRecordingContext create(Class... interfaces) {
        MethodCallRecorder methodCallRecorder = new MethodCallRecorderImpl();
        return new MethodCallRecordingContext(methodCallRecorder, createProxy(interfaces, methodCallRecorder));
    }

    private Object createProxy(Class[] interfaces, MethodCallRecorder methodCallRecorder) {
        return Proxy.newProxyInstance(interfaces[0].getClassLoader(), interfaces, new ProxyInteractionRecorderHandlerImpl(methodCallRecorder));
    }
}
