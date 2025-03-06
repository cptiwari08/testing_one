package com.atotech.backendfeatures.stepdefs.product;

import com.atotech.backendfeatures.helpers.items.ItemsHelper;
import com.atotech.backendfeatures.helpers.products.ProductsHelper;
import cucumber.api.java.en.Given;
import de.hybris.platform.core.model.product.ProductModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class ProductStepDefs {

    @Resource
    private ProductsHelper productsHelper;
    @Resource
    private ItemsHelper itemsHelper;

    @Given("^Product created with code \"([^\"]*)\" in catalog version \"([^\"]*)\" and has next attributes")
    public void productCreatedWithAttributes(String productCode, String catalogAndVersion, Map<String, String> expectedValues) {
        String[] splittedCatalogAndVersion = catalogAndVersion.split(":");
        ProductModel product = productsHelper.getProduct(productCode, splittedCatalogAndVersion[0], splittedCatalogAndVersion[1]);

        itemsHelper.assertThatItemHasExpectedValues(product, expectedValues);
    }


}
