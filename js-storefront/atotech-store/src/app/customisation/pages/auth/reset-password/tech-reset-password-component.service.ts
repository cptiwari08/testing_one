import { Injectable } from '@angular/core';
import { ResetPasswordComponentService } from '@spartacus/user/profile/components';
import { UserPasswordFacade } from '@spartacus/user/profile/root';
import { GlobalMessageService, RoutingService } from '@spartacus/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CustomFormValidators } from '@spartacus/storefront';

@Injectable()
export class TechResetPasswordComponentService extends ResetPasswordComponentService {
  constructor(
    protected userPasswordService: UserPasswordFacade,
    protected routingService: RoutingService,
    protected globalMessage: GlobalMessageService
  ) {
    super(userPasswordService, routingService, globalMessage);
  }

  form: FormGroup = new FormGroup(
    {
      password: new FormControl('', [
        Validators.required,
        CustomFormValidators.passwordValidator,
      ]),
    },
  );
}
