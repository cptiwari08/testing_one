import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'tech-become-customer',
  templateUrl: './become-customer.component.html',
  styleUrls: ['./become-customer.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BecomeCustomerComponent {
  constructor() {}
}
