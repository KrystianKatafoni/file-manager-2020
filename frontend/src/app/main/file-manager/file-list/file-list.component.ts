import {AfterViewInit, Component, ElementRef, OnDestroy, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {FileDataSource} from "../file-data-source";
import {FileInfo} from "../../../shared/file-info";
import {merge, Subject} from "rxjs";
import {MatPaginator} from "@angular/material/paginator";
import {FileListService} from "./file-list.service";
import {FileManagerService} from "../file-manager.service";
import {takeUntil, tap} from "rxjs/operators";
import {MatSort} from "@angular/material/sort";
import { MatSnackBar } from '@angular/material/snack-bar';
import * as FileSaver from "file-saver";
import {FileDto} from "../../../shared/file-dto";

@Component({
  selector: 'file-list',
  templateUrl: './file-list.component.html',
  styleUrls: ['./file-list.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class FileListComponent implements OnInit, AfterViewInit, OnDestroy {
  dataSource: FileDataSource;
  displayedColumns = ['index', 'name','extension','size', 'owner', 'creationDate'];
  selected: FileInfo = null;
  filterValue: string = '';
  @ViewChild("fileUpload", {static: false}) fileUpload: ElementRef;files  = [];
  private _unsubscribeAll: Subject<any>;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  constructor(
    private fileListService: FileListService,
    private fileManagerService: FileManagerService,
    private snackBar: MatSnackBar
  ) {
    this._unsubscribeAll = new Subject();
  }

  ngOnInit(): void {
    this.dataSource = new FileDataSource(this.fileManagerService);
    this.dataSource.loadFiles( 'name,asc',0,10, this.filterValue);
    this.fileListService.selectedFileSubject
      .pipe(takeUntil(this._unsubscribeAll))
      .subscribe(selected => {
        this.selected = selected;
      });
    this.fileListService.dataSourceRefreshDemand.subscribe(
      res => {
        if (res === true) {
          console.log('refresh demand');
          this.loadDocumentPage();
        }
      }
    );
  }

  ngAfterViewInit(): void {
/*    this.paginator.page
      .pipe(
        takeUntil(this._unsubscribeAll),
        tap(() => this.loadDocumentPage())
      )
      .subscribe();*/
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        tap(() => this.loadDocumentPage())
      )
      .subscribe();
  }
  /**
   * On destroy
   */
  ngOnDestroy(): void
  {
    this.fileListService.dataSourceRefreshDemand.next(null);
    // Unsubscribe from all subscriptions
    this._unsubscribeAll.next();
    this._unsubscribeAll.complete();
  }

  // -----------------------------------------------------------------------------------------------------
  // @ Public methods
  // -----------------------------------------------------------------------------------------------------

  /**
   * On select send to subject new document
   *
   * @param selected
   */
  onDoubleClick(selected: Document): void {
    this.downloadFile();
  }

  onSelect(selected: FileInfo): void {
    console.log(selected);
    this.fileListService.selectedFileSubject.next(selected);
  }
  loadDocumentPage(): void {
    this.dataSource.loadFiles(
      this.sort.active+','+this.sort.direction,
      this.paginator.pageIndex,
      this.paginator.pageSize,
      this.filterValue);
  }

  doFilter():void {
    this.loadDocumentPage();
    this.selected = null;
  }

  removefile() {
    this.fileManagerService.deleteFile(this.selected.id).subscribe( result => {
      let snackBarRef = this.snackBar.open('File ' + this.selected.name + ' successfully deleted',
        'DELETE',{
        duration: 3000
      });
      this.loadDocumentPage();
      this.selected = null;
    }, error => {
      console.log(error);
    })
  }
  downloadFile() {
    this.fileManagerService.getFile(this.selected.id).subscribe(result => {
      console.log(result);
      var blob = new Blob([result]
        , {type: "octet-stream"});
      FileSaver.saveAs(blob, this.selected.name+"."+this.selected.extension);
    }, error => {
      console.log(error);
    })
  }

  uploadFile(file: File) {
    this.fileManagerService.uploadFile(file).subscribe(result => {
      let snackBarRef = this.snackBar.open('File ' + file.name + ' successfully uploaded', 'UPLOAD',{
        duration: 3000
      });
      this.loadDocumentPage();
      this.selected = null;
    }, error => {
      console.log(error);
    })
  }

  onUploadFile() {
    const fileUpload = this.fileUpload.nativeElement;
    fileUpload.onchange = () => {
      const file = fileUpload.files[0];
      this.fileUpload.nativeElement.value = '';
      this.uploadFile(file);
    };
    fileUpload.click();
  }
}
