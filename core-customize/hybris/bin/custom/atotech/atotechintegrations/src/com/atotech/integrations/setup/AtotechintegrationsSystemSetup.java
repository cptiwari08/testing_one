package com.atotech.integrations.setup;

import de.hybris.platform.core.initialization.SystemSetup;

import com.atotech.integrations.constants.AtotechintegrationsConstants;

import java.io.InputStream;

@SystemSetup(extension = AtotechintegrationsConstants.EXTENSIONNAME)
public class AtotechintegrationsSystemSetup {

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData() {
	}

}
