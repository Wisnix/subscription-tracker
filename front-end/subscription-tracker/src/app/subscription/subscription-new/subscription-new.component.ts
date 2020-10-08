import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { SubscriptionService } from '../subscription.service';
import { switchMap } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { Subscription } from '../subscription.model';
import { AlertService } from 'src/app/shared/alert/alert.service';

@Component({
  selector: 'app-subscription-new',
  templateUrl: './subscription-new.component.html',
  styleUrls: ['./subscription-new.component.css'],
})
export class SubscriptionNewComponent implements OnInit {
  freePeriod: boolean = false;
  subscriptionForm: FormGroup;
  submitted: boolean = false;
  editMode: boolean = false;
  subscriptionId: string;

  options = {
    autoClose: true,
    keepAfterRouteChange: false,
  };

  constructor(
    private subscriptionService: SubscriptionService,
    private router: Router,
    private route: ActivatedRoute,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.route.params
      .pipe(
        switchMap((params) => {
          const id = params.id;
          if (id) {
            this.subscriptionId = id;
            this.editMode = true;
            return this.subscriptionService.getSubscription(id);
          }
          return new Observable();
        })
      )
      .subscribe((subscription: Subscription) => {
        if (subscription) {
          this.fillEditForm(subscription);
        }
      });
  }

  private initForm() {
    this.subscriptionForm = new FormGroup({
      name: new FormControl('', [Validators.required]),
      startDate: new FormControl('', [Validators.required]),
      price: new FormControl('', [Validators.required]),
      payInterval: new FormControl(null, [Validators.required]),
      freePeriod: new FormControl(''),
      status: new FormControl('active'),
      reminder: new FormControl(false),
    });
  }

  private fillEditForm(subscription: Subscription) {
    this.name.setValue(subscription.name);
    this.startDate.setValue(subscription.startDate);
    this.price.setValue(subscription.price);
    this.payInterval.setValue(subscription.payInterval);
    this.freePeriodControl.setValue(subscription.freePeriod);
    this.status.setValue(subscription.status);
    this.reminder.setValue(subscription.reminder);
    this.freePeriod = subscription.freePeriod ? true : false;
  }

  onCreateSubscription() {
    this.submitted = true;
    if (this.subscriptionForm.valid) {
      if (this.editMode) {
        this.subscriptionService
          .updateSubscription(this.subscriptionId, this.subscriptionForm.value)
          .subscribe((response) => {
            this.router.navigate(['/subscription']);
            this.alertService.info('Subscription updated.', this.options);
          });
      } else {
        this.subscriptionService
          .createSubscription(this.subscriptionForm.value)
          .subscribe((response) => {
            this.router.navigate(['/subscription']);
            this.alertService.success('Subscription created.', this.options);
          });
      }
    }
  }

  onSetToday() {
    const today = new Date();
    this.subscriptionForm
      .get('startDate')
      .setValue(today.toISOString().substring(0, 10));
  }

  onToggleFreePeriod() {
    this.freePeriod = !this.freePeriod;
    this.freePeriodControl.setValue('');
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
  get freePeriodControl() {
    return this.subscriptionForm.get('freePeriod');
  }
  get payInterval() {
    return this.subscriptionForm.get('payInterval');
  }
  get status() {
    return this.subscriptionForm.get('status');
  }
  get reminder() {
    return this.subscriptionForm.get('reminder');
  }
}
