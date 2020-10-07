import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SubscriptionListComponent } from './subscription-list/subscription-list.component';
import { SubscriptionNewComponent } from './subscription-new/subscription-new.component';
import { SubscriptionSelectComponent } from './subscription-select/subscription-select.component';

const routes: Routes = [
  { path: '', component: SubscriptionListComponent },
  { path: 'new', component: SubscriptionNewComponent },
  { path: ':id/edit', component: SubscriptionNewComponent },
  { path: ':id', component: SubscriptionSelectComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SubscriptionRoutingModule {}
