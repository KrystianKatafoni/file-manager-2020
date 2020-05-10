import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {TokenService} from "./token.service";
import {interval, Observable} from "rxjs";
import {User} from "../../shared/user";
import {filter, switchMap, tap} from "rxjs/operators";
import {Tokens} from "../model/tokens";
import {environment} from "../../../environments/environment";
import {RefreshToken} from "../model/refresh-token";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly endpoint = environment.endpoint;
  constructor(
    private http: HttpClient,
    public router: Router,
    private tokenService: TokenService
  ) { }

  login(user: User): Observable<any> {
    return this.http.post<any>(`${this.endpoint}/login`, user, {observe: 'response'})
      .pipe(
        tap(res => {
            const tokens: Tokens = JSON.parse(res.headers.get('Authorization')) as Tokens;
            tokens.accessToken = tokens.accessToken.replace('Bearer ', '');
            tokens.refreshToken = tokens.refreshToken.replace('Refresh ', '');
            this.doLoginUser(user.email, tokens);
            this.router.navigate(['']);
          }
        )
      );
  }
  logout(): void {
    this.tokenService.removeTokens();
    localStorage.removeItem('username');
    this.router.navigate(['login']);
  }

  get isLoggedIn(): boolean {
    return (this.tokenService.getAccessToken() !== null
      && this.tokenService.getRefreshToken() !== null) ? true : false;
  }

  private doLoginUser(username: string, tokens: Tokens): void {
    localStorage.setItem('username', username);
    this.tokenService.storeTokens(tokens);
  }

  getUsername(): string {
    return localStorage.getItem('username');
  }

  getAccessToken(): Observable<Tokens> {
    return this.http.post<any>(`${this.endpoint}/tokens/get-access-token`,
      { refresh_token: this.tokenService.getRefreshToken()})
      .pipe(
        tap((token: any) => {
          this.tokenService.storeAccessToken(token.access_token);
        }));
  }
  getRefreshToken(): Observable<any> {
    const refreshToken = this.tokenService.getRefreshToken();
    return this.http.post(`${this.endpoint}/tokens/get-refresh-token`, {
      refresh_token: refreshToken
    });
  }
  checkingRefreshTokenOnInterval(): void {
    const intervalTime = 1 * 60 * 1000;
    interval(intervalTime).pipe(
      filter(() => !this.tokenService.isRefreshTokenExpired()),
      switchMap(() => this.getRefreshToken())).subscribe((token: RefreshToken) => {
        console.log('Refresh token fetched: ' + token.refreshToken);
        this.tokenService.storeRefreshToken(token.refreshToken.replace('Refresh ', ''));
      },
      error => {
        if (this.tokenService.isAccessTokenExpired()) {
          console.log('Cannot refresh refreshToken');
          this.logout();
        }
      });
  }
}
