import { TestBed } from '@angular/core/testing';
import { TechResetPasswordComponentService } from './tech-reset-password-component.service';
import { UserPasswordFacade } from '@spartacus/user/profile/root';
import {
  GlobalMessageService,
  I18nTestingModule,
  RoutingService,
} from '@spartacus/core';
import { BehaviorSubject, of } from 'rxjs';
import createSpy = jasmine.createSpy;
import { ReactiveFormsModule } from '@angular/forms';
import { FormErrorsModule } from '@spartacus/storefront';

const resetToken = '123#Token';
const routerState$: BehaviorSubject<any> = new BehaviorSubject({
  state: {
    queryParams: {
      token: resetToken,
    },
  },
});

class MockUserPasswordFacade implements Partial<UserPasswordFacade> {
  reset() {
    return of({});
  }
}

class MockRoutingService {
  go = createSpy().and.stub();
  getRouterState() {
    return routerState$;
  }
}
class MockGlobalMessageService {
  add = createSpy().and.stub();
}

describe('TechResetPasswordComponentService', () => {
  let service: TechResetPasswordComponentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, I18nTestingModule, FormErrorsModule],
      providers: [
        {
          provide: TechResetPasswordComponentService,
          useClass: TechResetPasswordComponentService,
          deps: [UserPasswordFacade, RoutingService, GlobalMessageService],
        },
        {
          provide: UserPasswordFacade,
          useClass: MockUserPasswordFacade,
        },
        {
          provide: RoutingService,
          useClass: MockRoutingService,
        },
        {
          provide: GlobalMessageService,
          useClass: MockGlobalMessageService,
        },
      ],
    }).compileComponents();
    service = TestBed.inject(TechResetPasswordComponentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
