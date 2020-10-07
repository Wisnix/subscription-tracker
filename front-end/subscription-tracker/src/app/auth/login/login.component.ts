import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  signupSuccess: boolean = false;
  loginFailure: boolean = false;
  isAuth: boolean;
  constructor(
    private authService: AuthService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.isAuth = this.authService.isAuth();
    this.signupSuccess = this.route.snapshot.queryParams.signupSuccess;
    this.loginFailure = this.route.snapshot.queryParams.loginFailure;
  }

  onSubmit(form: NgForm) {
    this.authService.login({
      email: form.value.email,
      password: form.value.password,
    });
  }
}
