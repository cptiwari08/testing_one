package com.epam.aurora.cucumberserver.util.interaction.recorder;

public interface MethodCallRecordingContextFactory {
    MethodCallRecordingContext create(Class... interfaces);
}
