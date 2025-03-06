package com.epam.aurora.cucumberserver.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import de.hybris.platform.core.Registry;

import cucumber.api.java.ObjectFactory;
import cucumber.runtime.CucumberException;


public class HybrisSpringObjectFactory implements ObjectFactory {
	private Map<Class<?>, Object> instances = new HashMap<>();

	public static final String COULD_NOT_INSTANTIATE_BEAN_OF_TYPE_S = "Could not instantiate bean of type %s";

	@Override
	public <T> T getInstance(Class<T> type) {
		T instance = type.cast(instances.get(type));
		if (instance != null)
			return instance;

		return Optional.ofNullable(getBean(type))
				.orElseThrow(() -> new CucumberException(String.format(COULD_NOT_INSTANTIATE_BEAN_OF_TYPE_S, type)));
	}

	private <T> T getBean(Class<T> type) {
		T bean = Optional.ofNullable(Registry.getApplicationContext().getBean(type))
				.orElseThrow(() -> new CucumberException(String.format(COULD_NOT_INSTANTIATE_BEAN_OF_TYPE_S, type)));
		instances.put(type, bean);
		return bean;
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
	}

	@Override
	public boolean addClass(Class<?> type) {
		return true;
	}

}
