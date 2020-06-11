import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {FileInfo} from "../../../shared/file-info";

@Injectable()
export class FileListService {

  deletedFileSubject = new BehaviorSubject<FileInfo>(null);
  selectedFileSubject = new BehaviorSubject<FileInfo>(null);
  dataSourceRefreshDemand = new BehaviorSubject<boolean>(null);
}
