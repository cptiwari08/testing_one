package com.epam.aurora.cucumberserver.util.interaction.recorder;

import java.lang.reflect.Method;

public interface MethodCallRecorder {
    void applyRecordedMethodCalls(Object... objectsToApplyInteractions);

    void recordMethodCall(Method method, Object[] args);
}
