import { Component, OnInit } from '@angular/core';
import * as Chart from 'chart.js';
import * as dayjs from 'dayjs';
import { Subscription } from '../subscription/subscription.model';
import { SubscriptionService } from '../subscription/subscription.service';

@Component({
	selector: 'app-dashboard',
	templateUrl: './dashboard.component.html',
	styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
	subscriptions: Subscription[] = [];
	subscriptionsOnFreePeriod: Subscription[] = [];
	nextPayments: any;
	currentMonthlyExpenditure: number;
	activeSubscriptions: number;
	canvas: any;
	ctx: any;

	constructor(private subscriptionService: SubscriptionService) {}

	ngOnInit(): void {
		this.canvas = document.getElementById('myChart');
		this.ctx = this.canvas.getContext('2d');
		this.subscriptionService.getSubscriptions().subscribe((subscriptions) => {
			this.subscriptions = subscriptions;
			const expenditure = this.getTotalMonthlyExpenditure(this.subscriptions);
			this.initChart(expenditure);
			this.currentMonthlyExpenditure = expenditure[dayjs().month()];
			this.nextPayments = this.getNextPayments(this.subscriptions);
			this.activeSubscriptions = this.getActiveSubscriptionsCount(this.subscriptions);
			this.subscriptionsOnFreePeriod = this.getCurrentlyOnFreePeriod(this.subscriptions);
		});
	}

	private initChart(data: Array<number>) {
		const myChart = new Chart(this.ctx, {
			type: 'bar',
			data: {
				labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
				datasets: [
					{
						label: 'Total subscriptions cost in £',
						backgroundColor: 'rgb(255, 99, 132)',
						borderColor: 'rgb(255, 99, 132)',
						data: data,
					},
				],
			},
			options: {
				legend: {
					display: false,
				},
				responsive: false,
				title: {
					display: true,
					text: 'Expenditure by month',
				},
			},
		});
	}

	private getTotalMonthlyExpenditure(subscriptions: Subscription[]): Array<number> {
		let expenditure = new Array<number>(12);
		const now = dayjs();
		subscriptions.forEach((subscription) => {
			if (subscription.status === 'disabled') return subscription;
			let date = dayjs(subscription.startDate).add(subscription.freePeriod, 'd');
			const type: dayjs.OpUnitType = this.subscriptionService.getOpUnitType(subscription.payInterval);
			while (date.isBefore(now)) {
				const currentValue = expenditure[date.month()] || 0;
				if (date.year() == now.year()) expenditure[date.month()] = currentValue + subscription.price;
				date = date.add(1, type);
			}
		});
		return expenditure;
	}

	private compareByNextPayment(sub1: Subscription, sub2: Subscription) {
		const date1 = dayjs(sub1.nextPayment);
		const date2 = dayjs(sub2.nextPayment);
		if (date1.isBefore(date2)) {
			return -1;
		} else if (date1.isAfter(date2)) {
			return 1;
		} else {
			return 0;
		}
	}

	private getNextPayments(subscriptions: Subscription[]) {
		return subscriptions
			.slice()
			.sort(this.compareByNextPayment)
			.filter((sub, i, arr) => sub.nextPayment === arr[0].nextPayment);
	}

	private getActiveSubscriptionsCount(subscriptions: Subscription[]) {
		return subscriptions.filter((sub) => sub.status === 'active').length;
	}

	private getCurrentlyOnFreePeriod(subscriptions: Subscription[]) {
		return subscriptions.filter((sub) => sub.freePeriod > 0 && dayjs(sub.startDate).add(sub.freePeriod, 'd').isAfter(dayjs()));
	}
}
