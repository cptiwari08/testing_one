import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ForgotPasswordSuccessComponent } from './forgot-password-success.component';
import { SharedModule } from '../../../shared/shared.module';
import { BecomeCustomerModule } from '../common/become-customer/become-customer.module';

@NgModule({
  declarations: [ForgotPasswordSuccessComponent],
  imports: [CommonModule, SharedModule, BecomeCustomerModule],
})
export class ForgotPasswordSuccessModule {}
