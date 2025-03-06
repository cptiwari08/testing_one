package com.epam.aurora.cucumberserver.util.interaction.recorder;

import java.lang.reflect.Method;

public class MethodCall {
    private Method method;
    private Object[] args;

    public MethodCall(Method method, Object[] args) {
        this.method = method;
        this.args = args;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return args;
    }
}
