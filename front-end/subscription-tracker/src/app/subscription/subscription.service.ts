import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import * as dayjs from 'dayjs';
import { Subject, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AuthService } from '../auth/auth.service';
import { AlertService } from '../shared/alert/alert.service';
import { Subscription } from './subscription.model';

@Injectable({ providedIn: 'root' })
export class SubscriptionService {
	subscriptions: Subscription[];
	subscriptionsChanged = new Subject<Subscription[]>();

	private alertOptions = {
		autoClose: true,
		keepAfterRouteChange: false,
	};

	constructor(private http: HttpClient, private authService: AuthService, private router: Router, private alertService: AlertService) {}

	getSubscriptions() {
		const userId = this.authService.getUserId();
		return this.http.get<Subscription[]>(`http://localhost:8081/users/${userId}/subscriptions`).pipe(
			map((subscriptions) => {
				return (this.subscriptions = this.calculateNextPayment(subscriptions));
			})
		);
	}

	getSubscription(id: string) {
		return this.http.get<Subscription>(`http://localhost:8081/subscriptions/${id}`);
	}

	createSubscription(subscription: Subscription) {
		this.http.post<Subscription>('http://localhost:8081/subscriptions', subscription).subscribe((subscription) => {
			console.log('create');
			this.router.navigate(['/subscription']);
			this.alertService.success('Subscription created.', this.alertOptions);
		});
	}

	updateSubscription(id: string, subscription: Subscription) {
		this.http.put<Subscription>(`http://localhost:8081/subscriptions/${id}`, subscription).subscribe((subscription) => {
			console.log('update');
			this.router.navigate(['/subscription']);
			this.alertService.info('Subscription updated.', this.alertOptions);
		});
	}

	deleteSubscription(id: string) {
		this.http.delete(`http://localhost:8081/subscriptions/${id}`).subscribe((res) => {
			this.subscriptions.splice(
				this.subscriptions.findIndex((sub) => sub.id === id),
				1
			);
			this.subscriptionsChanged.next(this.subscriptions.slice());
			this.alertService.warn('Subscription deleted.', { autoClose: true });
		});
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

	private calculateNextPayment(subscriptions: Subscription[]): Subscription[] {
		return subscriptions.map((subscription) => {
			let startDate = dayjs(subscription.startDate);
			let now = dayjs();
			let type: dayjs.OpUnitType = this.getOpUnitType(subscription.payInterval);
			let nextPayment = dayjs(subscription.startDate).add(1, type);
			while (nextPayment.isBefore(now)) {
				nextPayment = nextPayment.add(1, type);
			}
			subscription.nextPayment = nextPayment.format('ddd DD MMM YYYY');
			return subscription;
		});
	}
}
