import {Component, OnInit, ViewChild} from '@angular/core';
import {AdministrationService} from "../administration.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {Extension} from "../../../shared/extension";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatDialog} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {CreateDialogComponent} from "../create-dialog/create-dialog.component";

@Component({
  selector: 'extension-list',
  templateUrl: './extension-list.component.html',
  styleUrls: ['./extension-list.component.scss']
})
export class ExtensionListComponent implements OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  filterValue = '';
  datasource;
  displayedColumns: string[] = ['index','extension', 'enabled', 'delete'];
  constructor(private administrationService: AdministrationService,
              private snackBar: MatSnackBar,
              private router:Router,
              public dialog: MatDialog) { }

  ngOnInit(): void {
    this.administrationService.getExtensions().subscribe(result => {
      console.log(result);
      this.datasource = new MatTableDataSource<Extension>(result);
      this.datasource.sort = this.sort;
      this.datasource.paginator = this.paginator;
    });
    this.administrationService.createdExtensionSubject.subscribe( extension => {
      this.createExtension(extension);
    })
  }

  onChange(row) {
    row.enabled = !row.enabled;
    this.updateExtension(row);
  }

  onDelete(row) {
    this.removeExtension(row);
  }

  onCreateExtension() {
    this.openCreateDialog();
  }

  removeExtension(extension) {
      this.administrationService.deleteExtension(extension.id).subscribe(result => {
        let snackBarRef = this.snackBar.open('Extension ' + extension.extension + ' successfully deleted',
          'DELETE',{
            duration: 3000
          });
        this.administrationService.getExtensions().subscribe(result => {
          console.log(result);
          this.datasource.data = result;
        });
        this.datasource.sort = this.sort;
        this.datasource.paginator = this.paginator;
      }, error => {
        let snackBarRef = this.snackBar.open('Extension ' + extension.extension + ' not deleted',
          'ERROR',{
            duration: 3000
          });
      });
  }
  updateExtension(extension) {
    this.administrationService.updateExtension(extension).subscribe(result => {
      let snackBarRef = this.snackBar.open('Extension ' + extension.extension + ' successfully updated',
        'UPDATE',{
          duration: 1000
        });
    }, error => {
      let snackBarRef = this.snackBar.open('Extension ' + extension.extension + ' not updated',
        'ERROR',{
          duration: 2000
        });
    });
  }
  createExtension(extension) {
    if(extension !== null) {
      this.administrationService.createExtension(extension).subscribe( result => {
        let snackBarRef = this.snackBar.open('Extension ' + extension.extension + ' successfully created',
          'CREATE',{
            duration: 2000
          });
        this.administrationService.getExtensions().subscribe(result => {
          this.datasource.data = result;
        });
        this.datasource.sort = this.sort;
        this.datasource.paginator = this.paginator;
      }, error => {
        let snackBarRef = this.snackBar.open(error.error.message,
          'ERROR',{
            duration: 2000
          });
      });
    }
  }

  public doFilter() {
    this.datasource.filter = this.filterValue.trim().toLocaleLowerCase();
  }


  public openCreateDialog(): void {
    const dialogRef = this.dialog.open(CreateDialogComponent,
      { width: '35%', height: '30%'});
    this.router.events.subscribe(() => {
      dialogRef.close();
    });

  }
}
