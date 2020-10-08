import { Injectable, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthData } from './auth.model';
import { Router } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private expirationTimer: any;
  private isAuthenticated: boolean;
  private token: string;
  private userId: string;

  constructor(private http: HttpClient, private router: Router) {}

  login(authData: AuthData) {
    return this.http.post<{
      token: string;
      expiration: string;
      userId: string;
    }>('http://localhost:8081/users/authenticate', authData);
  }

  processLoginResponse(response: {
    token: string;
    expiration: string;
    userId: string;
  }) {
    const token = response.token;
    const expirationTimestamp = Date.parse(response.expiration);
    const userId = response.userId;
    if (token) {
      this.token = token;
      this.userId = userId;
      this.isAuthenticated = true;
      this.setAuthTimer(expirationTimestamp);
      this.router.navigate(['/']);
      this.saveAuthData(token, new Date(expirationTimestamp), userId);
    }
  }

  signup(authData: AuthData) {
    return this.http.post('http://localhost:8081/users/signup', authData);
  }

  private saveAuthData(token: string, expiration: Date, userId: string) {
    localStorage.setItem('token', token);
    localStorage.setItem('expiration', expiration.toISOString());
    localStorage.setItem('userId', userId);
  }

  private clearAuthData() {
    localStorage.removeItem('token');
    localStorage.removeItem('expiration');
    localStorage.removeItem('userId');
  }

  autoAuthUser() {
    const token = localStorage.getItem('token');
    const userId = localStorage.getItem('userId');
    const expiration = Date.parse(localStorage.getItem('expiration'));
    if (!token) return;
    if (expiration > Date.now()) {
      this.token = token;
      this.isAuthenticated = true;
      this.setAuthTimer(expiration);
      this.userId = userId;
    }
  }

  private setAuthTimer(expirationTimestamp) {
    this.expirationTimer = setTimeout(() => {
      this.logout();
    }, expirationTimestamp - Date.now());
  }

  logout() {
    this.clearAuthData();
    this.isAuthenticated = false;
    this.token = null;
    this.expirationTimer = null;
    this.router.navigate(['/login']);
  }

  isAuth(): boolean {
    return this.isAuthenticated;
  }

  getToken() {
    return this.token;
  }

  getUserId() {
    return this.userId;
  }
}
