import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'tech-password',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PasswordComponent {
  constructor() {}
  @Input() parentForm: FormGroup;
  @Input() controlName: string;

  public isPasswordShown = false;

  public togglePasswordVisibility(): void {
    this.isPasswordShown = !this.isPasswordShown;
  }
}
