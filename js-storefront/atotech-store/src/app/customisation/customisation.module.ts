import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginPageModule } from './pages/auth/login/login-page.module';
import { ForgotPasswordModule } from './pages/auth/forgot-password/forgot-password.module';
import { ForgotPasswordSuccessModule } from './pages/auth/forgot-password-success/forgot-password-success.module';
import { ResetPasswordModule } from './pages/auth/reset-password/reset-password.module';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    LoginPageModule,
    ForgotPasswordModule,
    ForgotPasswordSuccessModule,
    ResetPasswordModule,
  ],
  exports: [LoginPageModule],
})
export class CustomisationModule {}
