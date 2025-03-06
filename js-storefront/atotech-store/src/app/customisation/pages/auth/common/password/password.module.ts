import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PasswordComponent } from './password.component';
import { SharedModule } from '../../../../shared/shared.module';
import { FormErrorsModule } from '@spartacus/storefront';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [PasswordComponent],
  imports: [CommonModule, SharedModule, FormErrorsModule, ReactiveFormsModule],
  exports: [PasswordComponent],
})
export class PasswordModule {}
