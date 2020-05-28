import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {File} from "../file";

@Injectable()
export class FileListService {

  deletedFileSubject = new BehaviorSubject<File>(null);
  selectedFileSubject = new BehaviorSubject<File>(null);
  dataSourceRefreshDemand = new BehaviorSubject<boolean>(null);
}
