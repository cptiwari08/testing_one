import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'app-forgot-password-success',
  templateUrl: './forgot-password-success.component.html',
  styleUrls: ['../common/common-styles.scss', 'forgot-password-success.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ForgotPasswordSuccessComponent {
  constructor() {}
}
