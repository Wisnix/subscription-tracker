import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subscription } from './subscription.model';

@Injectable({ providedIn: 'root' })
export class SubscriptionService {
  constructor(private http: HttpClient) {}

  createSubscription(subscription: Subscription) {
    return this.http.post('http://localhost:8081/subscriptions', subscription);
  }
}
