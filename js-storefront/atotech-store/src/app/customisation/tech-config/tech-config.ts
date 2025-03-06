import { translationChunksConfig } from '@spartacus/assets';
import { TechConfigInterface } from './tech-config.interface';
import { LoginPageComponent } from '../pages/auth/login/login-page.component';
import { ForgotPasswordComponent } from '../pages/auth/forgot-password/forgot-password.component';
import { ForgotPasswordSuccessComponent } from '../pages/auth/forgot-password-success/forgot-password-success.component';
import { NotAuthGuard } from '@spartacus/core';
import { ResetPasswordComponent } from '../pages/auth/reset-password/reset-password.component';

export const techConfig: TechConfigInterface = {
  i18n: {
    backend: {
      loadPath: 'assets/i18n-assets/{{lng}}/{{ns}}.json',
    },
    chunks: translationChunksConfig,
  },
  routing: {
    protected: true,
    routes: {
      forgotPasswordSuccess: {
        paths: ['login/forgot-password-success'],
        protected: false,
      },
    },
  },
  cmsComponents: {
    ReturningCustomerLoginComponent: {
      component: LoginPageComponent,
    },
    ForgotPasswordComponent: {
      component: ForgotPasswordComponent,
    },
    ForgotPasswordRequestSuccessComponent: {
      component: ForgotPasswordSuccessComponent,
      guards: [NotAuthGuard],
    },
    ResetPasswordComponent: {
      component: ResetPasswordComponent,
    },
  },
};
