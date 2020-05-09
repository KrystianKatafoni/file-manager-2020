import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FileManagerComponent } from './dashboard/file-manager.component';
import {RouterModule, Routes} from "@angular/router";
import {AuthGuard} from "../../auth/guard/auth.guard";

const routes: Routes = [
  { path: '', component: FileManagerComponent, canActivate: [AuthGuard]}
];
@NgModule({
  declarations: [FileManagerComponent],
  exports: [
    RouterModule
  ],
  imports: [
    CommonModule,
    [RouterModule.forChild(routes)]
  ]
})
export class FileManagerModule { }
