import { NgModule } from '@angular/core';
import { translationChunksConfig, translations } from "@spartacus/assets";
import { FeaturesConfig, I18nConfig, OccConfig, provideConfig, SiteContextConfig } from "@spartacus/core";
import { defaultB2bCheckoutConfig, defaultB2bOccConfig } from "@spartacus/setup";
import { defaultCmsContentProviders, layoutConfig, mediaConfig } from "@spartacus/storefront";
import {SmartEditConfig} from '@spartacus/smartedit/root';
import {environment} from "../../environments/environment";

@NgModule({
  declarations: [],
  imports: [
  ],
  providers: [provideConfig(layoutConfig), provideConfig(mediaConfig), ...defaultCmsContentProviders, provideConfig(<OccConfig>{
    backend: {
      occ: {
        baseUrl: environment.OCC_BASE_URL,
      }
    },
  }), provideConfig(<SiteContextConfig>{
    context: {
	  urlParameters: ['baseSite', 'language', 'currency'],
	  baseSite: ['atotechChina'],
      currency: ['USD'],
      language: ['en'],
    },
  }), provideConfig(<FeaturesConfig>{
    features: {
      level: '3.3'
    }
  }), provideConfig(defaultB2bOccConfig), provideConfig(defaultB2bCheckoutConfig),
    provideConfig(<SmartEditConfig>{
      smartEdit: {
        storefrontPreviewRoute: 'cx-preview',
        allowOrigin: environment.ALLOW_ORIGIN_URL,
      },
    })
]
})
export class SpartacusConfigurationModule { }
