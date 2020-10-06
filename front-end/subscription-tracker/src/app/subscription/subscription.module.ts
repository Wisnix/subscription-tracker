import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { SubscriptionListComponent } from './subscription-list/subscription-list.component';
import { SubscriptionNewComponent } from './subscription-new/subscription-new.component';
import { SubscriptionRoutingModule } from './subscription-routing.module';
import { SubscriptionSelectComponent } from './subscription-select/subscription-select.component';
import { SubscriptionComponent } from './subscription.component';

@NgModule({
  declarations: [
    SubscriptionListComponent,
    SubscriptionNewComponent,
    SubscriptionSelectComponent,
    SubscriptionComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    SubscriptionRoutingModule,
  ],
  exports: [],
})
export class SubscriptionModule {}
