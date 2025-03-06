import { I18nConfig } from '@spartacus/core/src/i18n/config/i18n-config';
import { RoutingConfig } from '@spartacus/core/src/routing/configurable-routes/config/routing-config';
import { LayoutConfig } from '@spartacus/storefront';

export interface TechConfigInterface
  extends I18nConfig,
    RoutingConfig,
    LayoutConfig {
  cmsComponents: any;
}
