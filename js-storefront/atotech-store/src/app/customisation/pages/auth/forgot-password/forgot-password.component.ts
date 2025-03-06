import { ChangeDetectionStrategy, Component } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { TechForgotPasswordComponentService } from './tech-forgot-password-component.service';

@Component({
  selector: 'tech-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['../common/common-styles.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ForgotPasswordComponent {
  constructor(protected service: TechForgotPasswordComponentService) {}

  form: FormGroup = this.service.form;
  isUpdating$ = this.service.isUpdating$;

  onSubmit(): void {
    this.service.requestEmail();
  }
}
