import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {BehaviorSubject, Observable} from "rxjs";

import {FileInfo} from "../../shared/file-info";


@Injectable()
export class FileManagerService {
  endpoint = environment.endpointApi;
  files: BehaviorSubject<Array<FileInfo>> = new BehaviorSubject([]);
  constructor(private httpClient: HttpClient) { }
  getFilesInfo(sort: string,
               pageNumber: number, pageSize: number, filteredName: string): Observable<FileInfo[]> {

   return this.httpClient.get<FileInfo[]>(this.endpoint + '/files',
      {
        params: new HttpParams()
          .set('sort', sort)
          .set('page', pageNumber.toString())
          .set('size', pageSize.toString())
          .set('name', filteredName)
      });
  }

  getFile(id: number): Observable<any> {
    const headers = new HttpHeaders();
    headers.set('Content-Type', 'application/octet-stream');
    const end = this.endpoint + '/files/'+id;
    return this.httpClient.get<any>(end, {headers: headers,responseType: 'arraybuffer' as 'json'});
  }

  /**
   * Upload files
   * @param file {FileInfo}
   */
  public uploadFile(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    console.log('upload');
    return this.httpClient.post<any>(`${this.endpoint}/files`, formData);
  }

  public deleteFile(id: number): Observable<any> {
    return this.httpClient.delete(this.endpoint + '/files/' + id);
  }
}
