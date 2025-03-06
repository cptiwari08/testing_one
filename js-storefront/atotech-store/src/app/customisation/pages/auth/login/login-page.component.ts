import { ChangeDetectionStrategy, Component, HostBinding } from '@angular/core';
import { LoginFormComponentService } from '@spartacus/user/account/components';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'cx-login-form',
  templateUrl: './login-page.component.html',
  styleUrls: ['../common/common-styles.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginPageComponent {
  constructor(protected service: LoginFormComponentService) {}

  form: FormGroup = this.service.form;
  isUpdating$ = this.service.isUpdating$;

  @HostBinding('class.user-form') style = true;

  onSubmit(): void {
    this.service.login();
  }
}
