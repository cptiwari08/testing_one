package com.epam.aurora.cucumberserver.util.interaction.recorder.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.SerializationUtils;

import com.epam.aurora.cucumberserver.util.interaction.recorder.MethodCall;
import com.epam.aurora.cucumberserver.util.interaction.recorder.MethodCallRecorder;
import cucumber.runtime.StepDefinitionMatch;

public class MethodCallRecorderImpl implements MethodCallRecorder {
    private List<MethodCall> interactions = new ArrayList<>();

    @Override
    public void applyRecordedMethodCalls(Object ... objectsToApplyInteractions) {
        interactions.forEach(interaction -> apply(interaction, objectsToApplyInteractions));
    }

    @Override
    public void recordMethodCall(Method method, Object[] args) {
        interactions.add(new MethodCall(method, copyArgs(args)));
    }

    private void apply(MethodCall interaction, Object[] objectsToApplyInteractions) {
        Object objectToApplyInteraction = findObjectWithSuitableInterface(interaction, objectsToApplyInteractions);
        try {
            interaction.getMethod().invoke(objectToApplyInteraction, interaction.getArgs());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(MessageFormat
                    .format("Recorder method: {0} with args: {1}, can not be executed on: {2}", interaction.getMethod(), interaction.getArgs(),
                            objectToApplyInteraction));
        }
    }

    private Object findObjectWithSuitableInterface(MethodCall interaction, Object[] objectsToApplyInteractions) {
        List<Object> matchedObjects = Arrays.stream(objectsToApplyInteractions)
                .filter(object -> interaction.getMethod().getDeclaringClass().isInstance(object))
                .collect(Collectors.toList());

        if (matchedObjects.size() > 1) {
            throw new IllegalStateException(MessageFormat
                    .format("Should be only one object which implements interface: {0} but was: {1}", interaction.getClass(), matchedObjects.size()));
        }

        return matchedObjects.stream().findFirst().orElseThrow(
                () -> new IllegalStateException(MessageFormat.format("Objects should contains one implementation of: {0}", matchedObjects.size())));
    }

    private Object[] copyArgs(Object[] args) {
        return Arrays.stream(args).map(this::tryCopyArg).toArray();
    }

    private Object tryCopyArg(Object arg) {
        if(shouldNotBeCopied(arg)) {
            return arg;
        }

        return copyArg(arg);
    }

    private boolean shouldNotBeCopied(Object arg) {
        List<Class> typesThatShouldNotBeCopied = Arrays.asList(StepDefinitionMatch.class);
        return arg == null || typesThatShouldNotBeCopied.stream().anyMatch(type -> type.isInstance(arg));
    }

    private Object copyArg(Object arg) {
        List<Function<Object, Optional>> copyStrategies = Arrays.asList(this::tryCopyWithSerialization, this::tryCopyWithClone);

        return copyStrategies.stream()
                .map(copyStrategy -> copyStrategy.apply(arg))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("Object: {0} can not be copied", arg)));
    }

    private Optional<Object> tryCopyWithSerialization(Object objectToCopy) {
        return Optional.of(objectToCopy)
                .filter(Serializable.class::isInstance)
                .flatMap(object -> invokeFunctionIgnoreExceptions(object, input -> SerializationUtils.clone((Serializable) input)));
    }

    private Optional<Object> tryCopyWithClone(Object objectToCopy) {
        return Optional.of(objectToCopy)
                .filter(Cloneable.class::isInstance)
                .flatMap(object -> invokeFunctionIgnoreExceptions(object, ObjectUtils::clone));
    }

    private <Input, Output> Optional<Output> invokeFunctionIgnoreExceptions(Input input, Function<Input, Output> function) {
        try {
            return Optional.of(function.apply(input));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    
}
