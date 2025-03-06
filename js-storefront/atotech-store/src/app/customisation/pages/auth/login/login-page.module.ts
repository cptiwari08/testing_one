import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginPageComponent } from './login-page.component';
import { SharedModule } from '../../../shared/shared.module';
import { ReactiveFormsModule } from '@angular/forms';
import { FormErrorsModule } from '@spartacus/storefront';
import { BecomeCustomerModule } from '../common/become-customer/become-customer.module';
import { PasswordModule } from '../common/password/password.module';

@NgModule({
  declarations: [LoginPageComponent],
  imports: [
    CommonModule,
    SharedModule,
    ReactiveFormsModule,
    FormErrorsModule,
    BecomeCustomerModule,
    PasswordModule,
  ],
})
export class LoginPageModule {}
