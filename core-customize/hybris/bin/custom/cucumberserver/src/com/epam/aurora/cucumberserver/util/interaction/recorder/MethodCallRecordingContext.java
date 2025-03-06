package com.epam.aurora.cucumberserver.util.interaction.recorder;

public class MethodCallRecordingContext {
    private MethodCallRecorder methodCallRecorder;
    private Object proxyToRecordMethodCalls;

    public MethodCallRecordingContext(MethodCallRecorder methodCallRecorder, Object proxyToRecordMethodCalls) {
        this.methodCallRecorder = methodCallRecorder;
        this.proxyToRecordMethodCalls = proxyToRecordMethodCalls;
    }

    public MethodCallRecorder getMethodCallRecorder() {
        return methodCallRecorder;
    }

    public Object getProxyToRecordMethodCalls() {
        return proxyToRecordMethodCalls;
    }
}
