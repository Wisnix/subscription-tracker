import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
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
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.signupFailure = this.route.snapshot.queryParams.signupFailure;
  }

  onSignup(form: NgForm) {
    this.authService.signup({
      email: form.value.email,
      password: form.value.password,
    });
  }
}
