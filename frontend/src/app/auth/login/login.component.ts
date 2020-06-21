import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../service/auth.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  private readonly durationInSeconds = 5;
  loginForm: FormGroup;
  constructor( private formBuilder: FormBuilder,
               private authService: AuthService,
               private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }
  loginUser(): void {
    this.authService.login(this.loginForm.value).subscribe(
      res => {

      }, error => {
        this.openSnackBar( error.error.message,'Error');
      }
    );
  }
  openSnackBar(message: string, label: string): void {
    this.snackBar.open(message, label, {
      duration: this.durationInSeconds * 1000,
    } );
  }
}
