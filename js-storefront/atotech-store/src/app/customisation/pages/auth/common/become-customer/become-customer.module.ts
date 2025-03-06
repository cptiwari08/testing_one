import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BecomeCustomerComponent } from './become-customer.component';
import { I18nModule } from '@spartacus/core';

@NgModule({
  declarations: [BecomeCustomerComponent],
  imports: [CommonModule, I18nModule],
  exports: [BecomeCustomerComponent],
})
export class BecomeCustomerModule {}
