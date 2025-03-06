import { ChangeDetectionStrategy, Component } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { TechResetPasswordComponentService } from './tech-reset-password-component.service';

@Component({
  selector: 'tech-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['../common/common-styles.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ResetPasswordComponent {
  constructor(protected service: TechResetPasswordComponentService) {}

  form: FormGroup = this.service.form;
  isUpdating$ = this.service.isUpdating$;
  token$: Observable<string> = this.service.resetToken$;

  onSubmit(token: string): void {
    this.service.resetPassword(token);
  }
}
