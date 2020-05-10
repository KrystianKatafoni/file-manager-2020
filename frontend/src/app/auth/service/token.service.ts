import { Injectable } from '@angular/core';
import {JwtHelperService} from "@auth0/angular-jwt";
import {Tokens} from "../model/tokens";

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() { }
  private readonly ACCESS_TOKEN = 'ACCESS_TOKEN';
  private readonly REFRESH_TOKEN = 'REFRESH_TOKEN';
  private jwtHelper = new JwtHelperService();

  isRefreshTokenExpired(): boolean {
    return this.jwtHelper.isTokenExpired(this.getRefreshToken());
  }

  isAccessTokenExpired(): boolean {
    return this.jwtHelper.isTokenExpired(this.getAccessToken());
  }

  getAccessToken(): string {
    return localStorage.getItem(this.ACCESS_TOKEN);
  }

  getRefreshToken(): string {
    return localStorage.getItem(this.REFRESH_TOKEN);
  }

  storeAccessToken(token: string): void {
    localStorage.setItem(this.ACCESS_TOKEN, token);
  }

  storeRefreshToken(token: string): void {
    localStorage.setItem(this.REFRESH_TOKEN, token);
  }

  storeTokens(tokens: Tokens): void {
    localStorage.setItem(this.ACCESS_TOKEN, tokens.accessToken);
    localStorage.setItem(this.REFRESH_TOKEN, tokens.refreshToken);
  }

  removeTokens(): void {
    localStorage.removeItem(this.ACCESS_TOKEN);
    localStorage.removeItem(this.REFRESH_TOKEN);
  }
}
