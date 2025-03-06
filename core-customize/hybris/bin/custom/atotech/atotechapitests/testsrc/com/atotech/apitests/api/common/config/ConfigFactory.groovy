package com.atotech.apitests.api.common.config

import com.atotech.apitests.constants.AtotechapitestsConstants

class ConfigFactory {

    private static final String DEFAULT_PROPERTY_PATH = '/atotechapitests/api-testing-properties.groovy'
    private static final String DEFAULT_VERSION = 'v2'

    private static Map<String, ConfigObject> configsCache = new HashMap<>()

    static synchronized ConfigObject getOrCreateConfig(String version, String propertyFileClassPath) {
        String key = version + propertyFileClassPath
        if (configsCache.containsKey(key)) {
            return configsCache.get(key)
        } else {
            ConfigObject config = createConfigInternal(propertyFileClassPath)
            configsCache.put(key, config)
            return config
        }
    }
    
    static ConfigObject getDefaultConfig() {
        return getOrCreateConfig(DEFAULT_VERSION, DEFAULT_PROPERTY_PATH)
    }

     static ConfigObject createConfigInternal(String propertyFileClassPath) {
        String configScript = AtotechapitestsConstants.class.getResource(propertyFileClassPath).text

         if (configScript == null) {
             throw new IllegalArgumentException('Could not find config file. Path - ${propertyFileClassPath}')
         }

         return new ConfigSlurper().parse(configScript)
    }
}
