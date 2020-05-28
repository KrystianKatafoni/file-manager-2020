import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {BehaviorSubject, Observable} from "rxjs";
import {File} from "./file";

@Injectable()
export class FileManagerService {
  endpoint = environment.endpointApi;
  files: BehaviorSubject<Array<File>> = new BehaviorSubject([]);
  constructor(private httpClient: HttpClient) { }
  getFiles(filter: string, sort: string,
               pageNumber: number, pageSize: number): Observable<File[]> {

   return this.httpClient.get<File[]>(this.endpoint + '/files',
      {
        params: new HttpParams()
          .set('sort', sort)
          .set('page', pageNumber.toString())
          .set('size', pageSize.toString())
      });
  }

  /**
   * Upload files
   * @param file {File}
   */
  public uploadFile(file: File): Observable<any> {
    const formData = new FormData();
    //formData.append('file', file);
    console.log('upload');
    return this.httpClient.post<any>(`${this.endpoint}/documents`, formData);
  }

  public deleteFile(id: number): Observable<any> {
    return this.httpClient.delete(this.endpoint + '/documents/' + id);
  }
}
