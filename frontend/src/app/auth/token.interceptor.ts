import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {BehaviorSubject, Observable, throwError} from 'rxjs';
import {AuthService} from './service/auth.service';
import {catchError, filter, switchMap, take} from 'rxjs/operators';
import {Router} from '@angular/router';
import {TokenService} from './service/token.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

    private isRefreshing = false;
    private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);

    constructor(private authService: AuthService,
                private router: Router,
                private tokenService: TokenService) {
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        if (this.authService.isLoggedIn) {
            req = this.addToken(req, this.tokenService.getAccessToken());

            return next.handle(req).pipe(catchError(error => {
                if (error instanceof HttpErrorResponse && error.status === 401) {
                    return this.handle401Error(req, next);
                } else {
                    return throwError(error);
                }
            }));
        }else {
            return next.handle(req);
        }

    }

    private addToken(req: HttpRequest<any>, accessToken: string): HttpRequest<any> {
        return req = req.clone({
            setHeaders: {
                Authorization: 'Bearer ' + accessToken
            }
        });
    }

    private handle401Error(request: HttpRequest<any>, next: HttpHandler): Observable<any> {

        if (this.tokenService.isRefreshTokenExpired()) {
            this.authService.logout();
            this.router.navigate(['login']);
        } else if (!this.isRefreshing) {
            this.isRefreshing = true;
            this.refreshTokenSubject.next(null);

            return this.authService.getAccessToken().pipe(
                switchMap((token: any) => {
                    this.isRefreshing = false;
                    this.refreshTokenSubject.next(token.access_token);
                    return next.handle(this.addToken(request, token.access_token));
                }));

        } else {
            return this.refreshTokenSubject.pipe(

                filter(token => token != null),
                take(1),
                switchMap(jwt => {
                    console.log('Waiting for token');
                    console.log(jwt);
                    return next.handle(this.addToken(request, jwt));
                }));
        }
    }
}
