import { Component, OnInit } from '@angular/core';
import { AuthService } from './auth/auth.service';
import { SubscriptionService } from './subscription/subscription.service';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
	title = 'subscription-tracker';

	constructor(private authService: AuthService, private subscriptionService: SubscriptionService) {}

	ngOnInit() {
		this.authService.autoAuthUser();
	}
}
