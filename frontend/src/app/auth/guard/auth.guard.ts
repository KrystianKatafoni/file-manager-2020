import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import { Observable } from 'rxjs';
import {AuthService} from "../service/auth.service";
import {TokenService} from "../service/token.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    public authService: AuthService,
    public router: Router,
    private tokenService: TokenService
  ) { }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.tokenService.isRefreshTokenExpired() || !this.authService.isLoggedIn) {
      this.authService.logout();
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }

}
