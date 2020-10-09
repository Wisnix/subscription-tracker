import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import * as dayjs from 'dayjs';
import { AuthService } from '../auth/auth.service';
import { Subscription } from './subscription.model';

@Injectable({ providedIn: 'root' })
export class SubscriptionService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  getSubscriptions() {
    const userId = this.authService.getUserId();
    return this.http.get<Subscription[]>(
      `http://localhost:8081/users/${userId}/subscriptions`
    );
  }

  getSubscription(id: string) {
    return this.http.get<Subscription>(
      `http://localhost:8081/subscriptions/${id}`
    );
  }

  createSubscription(subscription: Subscription) {
    return this.http.post('http://localhost:8081/subscriptions', subscription);
  }

  updateSubscription(id: string, subscription: Subscription) {
    return this.http.put(
      `http://localhost:8081/subscriptions/${id}`,
      subscription
    );
  }

  deleteSubscription(id: string) {
    return this.http.delete(`http://localhost:8081/subscriptions/${id}`);
  }

  getOpUnitType(payInterval: string): dayjs.OpUnitType {
    let type: dayjs.OpUnitType;
    switch (payInterval) {
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
    return type;
  }
}
