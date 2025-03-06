import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ForgotPasswordComponent } from './forgot-password.component';
import { SharedModule } from '../../../shared/shared.module';
import { ReactiveFormsModule } from '@angular/forms';
import { FormErrorsModule } from '@spartacus/storefront';
import { BecomeCustomerModule } from '../common/become-customer/become-customer.module';
import { TechForgotPasswordComponentService } from './tech-forgot-password-component.service';
import { UserPasswordFacade } from '@spartacus/user/profile/root';
import {
  AuthConfigService,
  GlobalMessageService,
  RoutingService,
} from '@spartacus/core';

@NgModule({
  declarations: [ForgotPasswordComponent],
  imports: [
    CommonModule,
    SharedModule,
    ReactiveFormsModule,
    FormErrorsModule,
    BecomeCustomerModule,
  ],
  providers: [
    {
      provide: TechForgotPasswordComponentService,
      useClass: TechForgotPasswordComponentService,
      deps: [
        UserPasswordFacade,
        RoutingService,
        AuthConfigService,
        GlobalMessageService,
      ],
    },
  ],
})
export class ForgotPasswordModule {}
