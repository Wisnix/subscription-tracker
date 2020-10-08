import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { FlashMessagesService } from 'angular2-flash-messages';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  isAuth: boolean;
  constructor(
    private authService: AuthService,
    private flashMessage: FlashMessagesService
  ) {}

  ngOnInit(): void {
    this.isAuth = this.authService.isAuth();
  }

  onSubmit(form: NgForm) {
    this.authService
      .login({
        email: form.value.email,
        password: form.value.password,
      })
      .subscribe(
        (response) => {
          this.authService.processLoginResponse(response);
        },
        (err) => {
          console.log(err);
          this.flashMessage.show('Wrong username or password', {
            cssClass: 'alert-danger',
            timeout: 5000,
          });
        }
      );
  }
}
