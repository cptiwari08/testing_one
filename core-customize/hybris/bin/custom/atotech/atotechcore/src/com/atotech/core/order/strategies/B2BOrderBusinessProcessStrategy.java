package com.atotech.core.order.strategies;

import de.hybris.platform.b2b.strategies.BusinessProcessStrategy;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.store.BaseStoreModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class B2BOrderBusinessProcessStrategy implements BusinessProcessStrategy {
    
    private static Logger LOG = LoggerFactory.getLogger(B2BOrderBusinessProcessStrategy.class);

    private BusinessProcessService businessProcessService;
    private ModelService modelService;
    private String processName;

    public B2BOrderBusinessProcessStrategy(BusinessProcessService businessProcessService, ModelService modelService,
                                           String processName) {
        this.businessProcessService = businessProcessService;
        this.modelService = modelService;
        this.processName = processName;
    }

    @Override
    public void createB2BBusinessProcess(OrderModel order) {
        BaseStoreModel store = order.getStore();
        String processName = store.getSubmitOrderProcessCode();

        if (StringUtils.isNotBlank(processName)) {
            createAndBusinessProcess(processName, order);
        }
        else {
            LOG.error("Unable to start fulfilment process for order {}}. Store {} has missing SubmitOrderProcessCode",
                    order.getCode(), store.getUid());
        }
    }

    protected void createAndBusinessProcess(String processName, OrderModel order) {
        String processCode = processName + order.getCode() + System.currentTimeMillis();
        OrderProcessModel businessProcessModel = businessProcessService.createProcess(
                processCode, processName);

        businessProcessModel.setOrder(order);
        modelService.save(businessProcessModel);
        businessProcessService.startProcess(businessProcessModel);
    }

    public String getProcessName() {
        return processName;
    }
}
