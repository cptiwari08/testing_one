package com.atotech.backendfeatures.setup;

import com.google.common.collect.Lists;
import de.hybris.platform.commerceservices.setup.AbstractSystemSetup;
import de.hybris.platform.core.initialization.SystemSetup;

import com.atotech.backendfeatures.constants.AtotechbackendfeaturesConstants;
import de.hybris.platform.core.initialization.SystemSetupContext;
import de.hybris.platform.core.initialization.SystemSetupParameter;
import de.hybris.platform.servicelayer.config.ConfigurationService;

import javax.annotation.Resource;
import java.util.List;

@SystemSetup(extension = AtotechbackendfeaturesConstants.EXTENSIONNAME)
public class AtotechbackendfeaturesSystemSetup extends AbstractSystemSetup {

	private static final String IMPORT_ENABLED = "atotechbackendfeatures.setup.data.import.enabled";

	@Resource
	private ConfigurationService configurationService;

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.PROJECT)
	public void createProjectData(final SystemSetupContext context) {
		if (isImportEnabled()) {
			importImpexFile(context, "/atotechbackendfeatures/import/projectdata-InboundProductUsers.impex");
		}
	}

	private boolean isImportEnabled() {
		return configurationService.getConfiguration().getBoolean(IMPORT_ENABLED, false);
	}

	@Override
	public List<SystemSetupParameter> getInitializationOptions() {
		return Lists.newArrayList();
	}
}
