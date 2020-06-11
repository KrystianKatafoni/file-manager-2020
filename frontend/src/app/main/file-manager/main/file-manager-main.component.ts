import { Component, OnInit } from '@angular/core';
import {FileDataSource} from "../file-data-source";
import {FileListService} from "../file-list/file-list.service";
import {FileInfo} from "../../../shared/file-info";
import {AuthService} from "../../../auth/service/auth.service";

@Component({
  selector: 'filemanager',
  templateUrl: './file-manager-main.component.html',
  styleUrls: ['./file-manager-main.component.scss']
})
export class FileManagerMainComponent implements OnInit {
  selectedFile: FileInfo;
  constructor(private fileListService: FileListService,
              private authService: AuthService) { }

  ngOnInit(): void {
    this.fileListService.selectedFileSubject.subscribe( selected => {
      this.selectedFile = selected;
    })
  }

  logout() {
    this.authService.logout();
  }
}
