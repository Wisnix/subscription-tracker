import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SubscriptionService } from '../subscription.service';

@Component({
  selector: 'app-subscription-new',
  templateUrl: './subscription-new.component.html',
  styleUrls: ['./subscription-new.component.css'],
})
export class SubscriptionNewComponent implements OnInit {
  freePeriod: boolean = false;
  subscriptionForm: FormGroup;
  submitted: boolean = false;

  constructor(
    private subscriptionService: SubscriptionService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.initForm();
  }

  private initForm() {
    this.subscriptionForm = new FormGroup({
      name: new FormControl('', [Validators.required]),
      startDate: new FormControl('', [Validators.required]),
      price: new FormControl('', [Validators.required]),
      payInterval: new FormControl(null, [Validators.required]),
      freePeriod: new FormControl(''),
      status: new FormControl('open'),
      reminder: new FormControl(false),
    });
  }

  onCreateSubscription() {
    this.submitted = true;
    if (this.subscriptionForm.valid) {
      this.subscriptionService
        .createSubscription(this.subscriptionForm.value)
        .subscribe((response) => {
          this.router.navigate(['/subscription']);
        });
    }
  }

  onSetToday() {
    const today = new Date();
    this.subscriptionForm
      .get('startDate')
      .setValue(today.toISOString().substring(0, 10));
  }

  get name() {
    return this.subscriptionForm.get('name');
  }
  get startDate() {
    return this.subscriptionForm.get('startDate');
  }
  get price() {
    return this.subscriptionForm.get('price');
  }
  get payInterval() {
    return this.subscriptionForm.get('payInterval');
  }
}
