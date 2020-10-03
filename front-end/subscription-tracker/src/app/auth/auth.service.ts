import { Injectable, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthData } from './auth.model';
import { Router } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private expirationTimer: any;
  private isAuthenticated: boolean;
  private token: string;

  constructor(private http: HttpClient, private router: Router) {}

  login(authData: AuthData) {
    return this.http
      .post<{ token: string; expiration: string }>(
        'http://localhost:8081/users/authenticate',
        authData
      )
      .subscribe((response) => {
        const token = response.token;
        const expirationTimestamp = Date.parse(response.expiration);
        if (token) {
          this.token = token;
          this.isAuthenticated = true;
          this.setAuthTimer(expirationTimestamp);
          this.router.navigate(['/']);
          this.saveAuthData(token, new Date(expirationTimestamp));
        }
      });
  }
  private saveAuthData(token: string, expiration: Date) {
    localStorage.setItem('token', token);
    localStorage.setItem('expiration', expiration.toISOString());
  }

  private clearAuthData() {
    localStorage.removeItem('token');
    localStorage.removeItem('expiration');
  }

  autoAuthUser() {
    const token = localStorage.getItem('token');
    const expiration = Date.parse(localStorage.getItem('expiration'));
    if (!token) return;
    if (expiration > Date.now()) {
      this.token = token;
      this.isAuthenticated = true;
      this.setAuthTimer(expiration);
    }
  }

  private setAuthTimer(expirationTimestamp) {
    console.log('setting timer to ' + (expirationTimestamp - Date.now()));
    this.expirationTimer = setTimeout(() => {
      this.logout();
    }, expirationTimestamp - Date.now());
  }

  private logout() {
    this.clearAuthData();
    this.isAuthenticated = false;
    this.token = null;
    this.expirationTimer = null;
  }

  isAuth(): boolean {
    return this.isAuthenticated;
  }
}
