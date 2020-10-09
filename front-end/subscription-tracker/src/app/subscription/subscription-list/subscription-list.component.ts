import { Component, OnInit, TemplateRef } from '@angular/core';
import { Subscription } from '../subscription.model';
import { SubscriptionService } from '../subscription.service';
import * as dayjs from 'dayjs';
import { ActivatedRoute, Router } from '@angular/router';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { AlertService } from 'src/app/shared/alert/alert.service';

@Component({
  selector: 'app-subscription-list',
  templateUrl: './subscription-list.component.html',
  styleUrls: ['./subscription-list.component.css'],
})
export class SubscriptionListComponent implements OnInit {
  modalRef: BsModalRef;
  subscriptions: Subscription[] = [];
  subscriptionToDelete: string;
  constructor(
    private subscriptionService: SubscriptionService,
    private route: ActivatedRoute,
    private router: Router,
    private modalService: BsModalService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.subscriptionService.getSubscriptions().subscribe((subscriptions) => {
      if (subscriptions.length > 0) {
        this.subscriptions = this.calculateNextPayment(subscriptions);
      }
    });
  }

  private calculateNextPayment(subscriptions: Subscription[]): Subscription[] {
    return subscriptions.map((subscription) => {
      let startDate = dayjs(subscription.startDate);
      let now = dayjs();
      let type: dayjs.OpUnitType = this.subscriptionService.getOpUnitType(subscription.payInterval);
      let nextPayment=dayjs(subscription.startDate).add(1, type);
      while (nextPayment.isBefore(now)) {
        nextPayment=nextPayment.add(1, type);
      } 
      subscription.nextPayment = nextPayment.format('ddd DD MMM YYYY');
      return subscription;
    });
  }

  onEditSubscription(id: string) {
    this.router.navigate([id, 'edit'], { relativeTo: this.route });
  }

  onDeleteSubscription(template: TemplateRef<any>, id: string) {
    this.modalRef = this.modalService.show(template, { class: 'modal-sm' });
    this.subscriptionToDelete = id;
  }

  confirm(): void {
    this.modalRef.hide();
    this.subscriptionService
      .deleteSubscription(this.subscriptionToDelete)
      .subscribe(() => {
        this.subscriptions.splice(
          this.subscriptions.findIndex(
            (s) => s.id === this.subscriptionToDelete
          )
        );
        this.alertService.warn('Subscription deleted.', { autoClose: true });
      });
    this.subscriptionToDelete = null;
  }

  decline(): void {
    this.modalRef.hide();
    this.subscriptionToDelete = null;
  }
}
