import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ResetPasswordComponent } from './reset-password.component';
import { SharedModule } from '../../../shared/shared.module';
import { ReactiveFormsModule } from '@angular/forms';
import { FormErrorsModule } from '@spartacus/storefront';
import { BecomeCustomerModule } from '../common/become-customer/become-customer.module';
import { PasswordModule } from '../common/password/password.module';
import { UserPasswordFacade } from '@spartacus/user/profile/root';
import { GlobalMessageService, RoutingService } from '@spartacus/core';
import { TechResetPasswordComponentService } from './tech-reset-password-component.service';

@NgModule({
  declarations: [ResetPasswordComponent],
  imports: [
    CommonModule,
    SharedModule,
    ReactiveFormsModule,
    FormErrorsModule,
    BecomeCustomerModule,
    PasswordModule,
  ],
  providers: [
    {
      provide: TechResetPasswordComponentService,
      useClass: TechResetPasswordComponentService,
      deps: [UserPasswordFacade, RoutingService, GlobalMessageService],
    },
  ],
})
export class ResetPasswordModule {}
