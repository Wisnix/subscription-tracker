import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FlashMessagesService } from 'angular2-flash-messages';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
})
export class SignupComponent implements OnInit {
  signupFailure: boolean;
  constructor(
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router,
    private flashMessage: FlashMessagesService
  ) {}

  ngOnInit(): void {
    this.signupFailure = this.route.snapshot.queryParams.signupFailure;
  }

  onSignup(form: NgForm) {
    this.authService
      .signup({
        email: form.value.email,
        password: form.value.password,
      })
      .subscribe(
        (response) => {
          if (response) {
            this.flashMessage.show(
              "Account created, now you can <a routerLink='/login'>Log in</a>",
              {
                cssClass: 'alert-success',
                timeout: 5000,
              }
            );
            form.reset();
          }
        },
        (err) => {
          this.flashMessage.show('This email is already taken', {
            cssClass: 'alert-danger',
            timeout: 5000,
          });
        }
      );
  }
}
