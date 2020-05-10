import {Component, OnInit} from '@angular/core';
import {AuthService} from "./auth/service/auth.service";
import {TokenService} from "./auth/service/token.service";
import {RefreshToken} from "./auth/model/refresh-token";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  title = 'frontend';
  constructor(
    private authService: AuthService,
    private tokenService: TokenService
  ){}
  ngOnInit(): void
  {
    this.refreshTokenOnStartup();
    this.authService.checkingRefreshTokenOnInterval();
  }
  private refreshTokenOnStartup(): void {
    if (!this.tokenService.isRefreshTokenExpired()){
      this.authService.getRefreshToken().subscribe(
        (token: RefreshToken) => {
          console.log('Refreshing token startup');
          this.tokenService.storeRefreshToken(token.refreshToken.replace('Refresh ', ''));
        },
        error => {
          console.log('Cannot refresh refreshToken');
          this.authService.logout();
        });
    }

  }
}
