import { Component, OnInit } from '@angular/core';
import { Subscription } from '../subscription.model';
import { SubscriptionService } from '../subscription.service';
import * as dayjs from 'dayjs';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-subscription-list',
  templateUrl: './subscription-list.component.html',
  styleUrls: ['./subscription-list.component.css'],
})
export class SubscriptionListComponent implements OnInit {
  subscriptions: Subscription[] = [];
  constructor(
    private subscriptionService: SubscriptionService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.subscriptionService.getSubscriptions().subscribe((subscriptions) => {
      this.subscriptions = this.calculateNextPayment(subscriptions);
    });
  }

  private calculateNextPayment(subscriptions: Subscription[]): Subscription[] {
    return subscriptions.map((subscription) => {
      let startDate = dayjs(subscription.startDate);
      let now = dayjs();
      let type: dayjs.OpUnitType;
      switch (subscription.payInterval) {
        case 'week':
          type = 'w';
          break;
        case 'month':
          type = 'M';
          break;
        case 'year':
          type = 'y';
          break;
      }
      let nextPayment;
      do {
        nextPayment = dayjs(subscription.startDate).add(1, type);
      } while (nextPayment.isBefore(now));
      subscription.nextPayment = nextPayment.format('ddd DD MMM YYYY');
      return subscription;
    });
  }

  onEditSubscription(id: string) {
    this.router.navigate([id, 'edit'], { relativeTo: this.route });
  }

  onDeleteSubscription(id: string) {
    console.log(id);
  }
}
