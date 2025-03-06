import { Injectable } from '@angular/core';
import { ForgotPasswordComponentService } from '@spartacus/user/profile/components';
import {
  AuthConfigService,
  GlobalMessageService,
  OAuthFlow,
  RoutingService,
} from '@spartacus/core';
import { UserPasswordFacade } from '@spartacus/user/profile/root';

@Injectable()
export class TechForgotPasswordComponentService extends ForgotPasswordComponentService {
  constructor(
    protected userPasswordService: UserPasswordFacade,
    protected routingService: RoutingService,
    protected authConfigService: AuthConfigService,
    protected globalMessage: GlobalMessageService
  ) {
    super(
      userPasswordService,
      routingService,
      authConfigService,
      globalMessage
    );
  }

  protected onSuccess(): void {
    this.busy$.next(false);
    this.form.reset();
    this.redirect();
  }

  protected redirect(): void {
    if (
      this.authConfigService.getOAuthFlow() ===
      OAuthFlow.ResourceOwnerPasswordFlow
    ) {
      this.routingService.go({ cxRoute: 'forgotPasswordSuccess' });
    }
  }
}
