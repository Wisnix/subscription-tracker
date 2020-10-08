import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { DayDiffPipe } from '../shared/day-diff.pipe';
import { SubscriptionListComponent } from './subscription-list/subscription-list.component';
import { SubscriptionNewComponent } from './subscription-new/subscription-new.component';
import { SubscriptionRoutingModule } from './subscription-routing.module';
import { SubscriptionSelectComponent } from './subscription-select/subscription-select.component';
import { SubscriptionComponent } from './subscription.component';
import { ModalModule } from 'ngx-bootstrap/modal';

@NgModule({
  declarations: [
    SubscriptionListComponent,
    SubscriptionNewComponent,
    SubscriptionSelectComponent,
    SubscriptionComponent,
    DayDiffPipe,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    SubscriptionRoutingModule,
    ModalModule.forRoot(),
  ],
  exports: [],
})
export class SubscriptionModule {}
