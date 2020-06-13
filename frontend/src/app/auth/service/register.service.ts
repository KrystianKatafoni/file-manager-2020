import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';

import {RegisteredUser} from '../model/registered-user';

@Injectable({
    providedIn: 'root'
})
export class RegisterService {


    private readonly endpoint = environment.endpoint;

    constructor(
        private http: HttpClient,
    ) {}


    signUp(user: RegisteredUser): Observable<any> {
        const api = `${this.endpoint}/registration`;
        return this.http.post<any>(api, user);
    }


}
