import { Component } from '@angular/core';
import { HamburgerMenuService } from '@spartacus/storefront';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  constructor(private hamburgerMenuService: HamburgerMenuService) {}

  title = 'atotech-store';

  get isExpanded(): Observable<boolean> {
    return this.hamburgerMenuService.isExpanded;
  }
}
