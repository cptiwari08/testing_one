package com.epam.atotechinitaldata.setup;

import com.epam.atotechinitaldata.constants.AtotechinitaldataConstants;
import de.hybris.platform.commerceservices.dataimport.impl.CoreDataImportService;
import de.hybris.platform.commerceservices.dataimport.impl.SampleDataImportService;
import de.hybris.platform.commerceservices.setup.AbstractSystemSetup;
import de.hybris.platform.commerceservices.setup.data.ImportData;
import de.hybris.platform.commerceservices.setup.events.CoreDataImportedEvent;
import de.hybris.platform.commerceservices.setup.events.SampleDataImportedEvent;
import de.hybris.platform.core.initialization.SystemSetup;
import de.hybris.platform.core.initialization.SystemSetupContext;
import de.hybris.platform.core.initialization.SystemSetupParameter;
import de.hybris.platform.core.initialization.SystemSetupParameterMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SystemSetup(extension = AtotechinitaldataConstants.EXTENSIONNAME)
public class AtotechInitialDataSystemSetup extends AbstractSystemSetup {

    private static final String ATOTECH_CHINA = "atotechChina";

    private static final String IMPORT_CORE_DATA = "importCoreData";
    private static final String IMPORT_SAMPLE_DATA = "importSampleData";
    private static final String ACTIVATE_SOLR_CRON_JOBS = "activateSolrCronJobs";

    private CoreDataImportService coreDataImportService;
    private SampleDataImportService sampleDataImportService;

    @SystemSetupParameterMethod
    @Override
    public List<SystemSetupParameter> getInitializationOptions()
    {
        final List<SystemSetupParameter> params = new ArrayList<SystemSetupParameter>();

        params.add(createBooleanSystemSetupParameter(IMPORT_CORE_DATA, "Import Core Data", true));
        params.add(createBooleanSystemSetupParameter(IMPORT_SAMPLE_DATA, "Import Sample Data", true));
        params.add(createBooleanSystemSetupParameter(ACTIVATE_SOLR_CRON_JOBS, "Activate Solr Cron Jobs", true));

        return params;
    }

    @SystemSetup(type = SystemSetup.Type.PROJECT, process = SystemSetup.Process.INIT)
    public void createProjectData(final SystemSetupContext context)
    {
        final List<ImportData> importData = new ArrayList<ImportData>();

        final ImportData atotechChinaImportData = new ImportData();
        atotechChinaImportData.setProductCatalogName(ATOTECH_CHINA);
        atotechChinaImportData.setContentCatalogNames(Arrays.asList(ATOTECH_CHINA));
        atotechChinaImportData.setStoreNames(Arrays.asList(ATOTECH_CHINA));
        importData.add(atotechChinaImportData);

        getCoreDataImportService().execute(this, context, importData);
        getEventService().publishEvent(new CoreDataImportedEvent(context, importData));

        sampleDataImportService.execute(this, context, importData);
        importCommerceOrgData(context);
        getEventService().publishEvent(new SampleDataImportedEvent(context, importData));
    }

    public CoreDataImportService getCoreDataImportService()
    {
        return coreDataImportService;
    }

    public void setCoreDataImportService(CoreDataImportService coreDataImportService) {
        this.coreDataImportService = coreDataImportService;
    }

    public SampleDataImportService getSampleDataImportService() {
        return sampleDataImportService;
    }

    public void setSampleDataImportService(SampleDataImportService sampleDataImportService) {
        this.sampleDataImportService = sampleDataImportService;
    }

    private void importCommerceOrgData(final SystemSetupContext context){
        getSetupImpexService().importImpexFile(String.format("/%s/import/sampledata/commerceorg/user-groups.impex", context.getExtensionName()),
                false);
    }

}
