import { NgModule } from '@angular/core';
import { FileManagerMainComponent } from './main/file-manager-main.component';
import {RouterModule, Routes} from "@angular/router";
import {AuthGuard} from "../../auth/guard/auth.guard";
import {MatToolbarModule} from "@angular/material/toolbar";

import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatTooltipModule} from "@angular/material/tooltip";
import { StatisticsComponent } from './statistics/statistics.component';
import {FileListComponent} from "./file-list/file-list.component";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatTableModule} from "@angular/material/table";
import {MatListModule} from "@angular/material/list";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatOptionModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";
import {MatInputModule} from "@angular/material/input";
import {CommonModule} from "@angular/common";
import {FlexLayoutModule} from "@angular/flex-layout";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FileManagerService} from "./file-manager.service";
import {FileListService} from "./file-list/file-list.service";
import {MatSortModule} from "@angular/material/sort";
import {FormsModule} from "@angular/forms";
import {NgxFilesizeModule} from "ngx-filesize";

const routes: Routes = [
  { path: '', component: FileManagerMainComponent, canActivate: [AuthGuard]}
];
@NgModule({
  declarations: [FileManagerMainComponent, FileListComponent, StatisticsComponent],
  exports: [
    RouterModule
  ],
  imports: [
    RouterModule.forChild(routes),
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatTooltipModule,
    MatPaginatorModule,
    MatTableModule,
    MatListModule,
    MatFormFieldModule,
    MatOptionModule,
    MatSelectModule,
    MatInputModule,
    CommonModule,
    FlexLayoutModule,
    BrowserAnimationsModule,
    MatSortModule,
    FormsModule,
    NgxFilesizeModule

  ],
  providers   : [
    FileManagerService,
    FileListService
  ],
})
export class FileManagerModule {

}
