package com.epam.aurora.cucumberserver.runtime.config.glue;

import java.util.List;

public interface GlueCodeLocationsFactory {
    String GLUE_FROM_CLASSPATH_TEMPLATE = "classpath:{0}";

    List<String> get();
}
