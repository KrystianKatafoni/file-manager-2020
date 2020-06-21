import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Extension} from "../../shared/extension";

@Injectable({
  providedIn: 'root'
})
export class AdministrationService {
  endpoint = environment.endpointApi;
  createdExtensionSubject = new BehaviorSubject<Extension>(null);
  constructor(private httpClient: HttpClient) {
  }

  getExtensions(): Observable<Extension[]> {
    return this.httpClient.get<Extension[]>(this.endpoint + '/extension');
  }

  public deleteExtension(id: number): Observable<any> {
    return this.httpClient.delete<any>(this.endpoint + '/extension/' + id);
  }
  public createExtension(extension: Extension): Observable<any> {
    return this.httpClient.post<Extension>(`${this.endpoint}/extension`, extension);
  }
  public updateExtension(extension: Extension): Observable<any> {
    return this.httpClient.put<Extension>(`${this.endpoint}/extension`, extension);
  }
}
