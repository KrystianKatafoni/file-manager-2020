import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {FileDataSource} from "../file-data-source";
import {File} from "../file";
import {Subject} from "rxjs";
import {MatPaginator} from "@angular/material/paginator";
import {FileListService} from "./file-list.service";
import {FileManagerService} from "../file-manager.service";
import {takeUntil, tap} from "rxjs/operators";

@Component({
  selector: 'file-list',
  templateUrl: './file-list.component.html',
  styleUrls: ['./file-list.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class FileListComponent implements OnInit, AfterViewInit, OnDestroy {
  dataSource: FileDataSource;
  displayedColumns = ['index', 'name', 'owner', 'created'];
  selected: File;
  private _unsubscribeAll: Subject<any>;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  constructor(
    private fileListService: FileListService,
    private fileManagerService: FileManagerService
  ) {
    this._unsubscribeAll = new Subject();
  }

  ngOnInit(): void {
    this.dataSource = new FileDataSource(this.fileManagerService);
    this.dataSource.loadFiles( '',
      'name,asc',
      0,
      10);
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
    this.paginator.page
      .pipe(
        takeUntil(this._unsubscribeAll),
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
    console.log('double click')
  }

  onSelect(selected: File): void {

    this.fileListService.selectedFileSubject.next(selected);
  }
  loadDocumentPage(): void {
    this.dataSource.loadFiles(
      '',
      'name,asc',
      this.paginator.pageIndex,
      this.paginator.pageSize);
  }
}
