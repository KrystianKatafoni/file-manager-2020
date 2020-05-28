import { Component, OnInit } from '@angular/core';
import {FileDataSource} from "../file-data-source";
import {FileListService} from "../file-list/file-list.service";
import {File} from "../file";

@Component({
  selector: 'filemanager',
  templateUrl: './file-manager-main.component.html',
  styleUrls: ['./file-manager-main.component.scss']
})
export class FileManagerMainComponent implements OnInit {
  selectedFile: File;
  constructor(private fileListService: FileListService) { }

  ngOnInit(): void {
    this.fileListService.selectedFileSubject.subscribe( selected => {
      this.selectedFile = selected;
    })
  }

}
