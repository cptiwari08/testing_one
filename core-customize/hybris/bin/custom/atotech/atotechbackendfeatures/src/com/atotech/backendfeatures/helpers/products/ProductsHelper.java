package com.atotech.backendfeatures.helpers.products;


import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.session.SessionExecutionBody;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ProductsHelper {

    @Resource
    private ProductService productService;
    @Resource
    private CatalogVersionService catalogVersionService;
    @Resource
    private SessionService sessionService;
    @Resource
    private UserService userService;

    public ProductModel getProduct(String code, String catalog, String catalogVersionName) {
        return sessionService.executeInLocalView(new SessionExecutionBody() {
            @Override
            public Object execute() {
                CatalogVersionModel catalogVersion = catalogVersionService.getCatalogVersion(catalog, catalogVersionName);
                userService.setCurrentUser(userService.getAdminUser());
                return productService.getProductForCode(catalogVersion, code);
            }
        });
    }
}
