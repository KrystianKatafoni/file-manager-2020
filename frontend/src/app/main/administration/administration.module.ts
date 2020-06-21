import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {MainAdminComponent} from './main-admin/main-admin.component';
import {MainAdminGuard} from "../../auth/guard/main-admin.guard";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatTooltipModule} from "@angular/material/tooltip";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatTableModule} from "@angular/material/table";
import {MatListModule} from "@angular/material/list";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatOptionModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";
import {MatInputModule} from "@angular/material/input";
import {FlexLayoutModule} from "@angular/flex-layout";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatSortModule} from "@angular/material/sort";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { ExtensionListComponent } from './extension-list/extension-list.component';
import {AdministrationService} from "./administration.service";
import { CreateDialogComponent } from './create-dialog/create-dialog.component';
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatDialogModule} from "@angular/material/dialog";


const routes: Routes = [
  { path: 'administration', component: MainAdminComponent, canActivate: [MainAdminGuard]}
];
@NgModule({
  declarations: [MainAdminComponent, ExtensionListComponent, CreateDialogComponent],
  exports: [
    RouterModule
  ],
  imports: [
    CommonModule,
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
    MatCheckboxModule,
    MatDialogModule,
    ReactiveFormsModule
  ],
  providers: [
    AdministrationService
  ]
})
export class AdministrationModule { }
