import {Component, Inject, OnInit} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AdministrationService} from "../administration.service";
import {Extension} from "../../../shared/extension";
@Component({
  selector: 'create-dialog',
  templateUrl: './create-dialog.component.html',
  styleUrls: ['./create-dialog.component.scss']
})
export class CreateDialogComponent implements OnInit {
  extensionForm: FormGroup;
  constructor(public dialogRef: MatDialogRef<CreateDialogComponent>,
              private formBuilder: FormBuilder,
              private administrationService: AdministrationService) {
  }

  ngOnInit(): void {
    this.createForm();
  }

  createForm() {
    this.extensionForm = this.formBuilder.group(
      {
        'extension': [null, [Validators.required, Validators.minLength(2)]]
      }
    )
  }
  close(): void {
    this.dialogRef.close();
  }
  onSubmit() {
    const extension = new Extension();
    extension.extension = this.extensionForm.get('extension').value
    extension.enabled = true;
    this.administrationService.createdExtensionSubject.next(extension);
    this.extensionForm.reset();
    for (let name in this.extensionForm.controls) {
      this.extensionForm.controls[name].setErrors(null);
    }
  }
}
