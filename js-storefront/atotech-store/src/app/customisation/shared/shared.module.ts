import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  IconModule,
  SpinnerModule,
} from '@spartacus/storefront';
import { I18nModule, UrlModule } from '@spartacus/core';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    SpinnerModule,
    I18nModule,
    UrlModule,
    RouterModule,
    IconModule,
  ],
  exports: [SpinnerModule, I18nModule, UrlModule, RouterModule, IconModule],
})
export class SharedModule {}
