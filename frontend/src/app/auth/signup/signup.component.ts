import {Component, OnDestroy, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {RegisterService} from "../service/register.service";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Subject} from "rxjs";
import {takeUntil} from "rxjs/operators";

@Component({
  selector: 'signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit, OnDestroy {
  registerForm: FormGroup;
  private readonly durationInSeconds = 5;
  private _unsubscribeAll: Subject<any>;
  constructor(private formBuilder: FormBuilder,
              private registerService: RegisterService,
              private router: Router,
              private snackBar: MatSnackBar) {
    this._unsubscribeAll = new Subject();
  }
  ngOnDestroy(): void
  {
    // Unsubscribe from all subscriptions
    this._unsubscribeAll.next();
    this._unsubscribeAll.complete();
  }

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      matchingPassword: ['', [Validators.required, confirmPasswordValidator]],
      registrationCode: ['', Validators.required]
    });
    // Update the validity of the 'passwordConfirm' field
    // when the 'password' field changes
    this.registerForm.get('password').valueChanges
      .pipe(takeUntil(this._unsubscribeAll))
      .subscribe(() => {
        this.registerForm.get('matchingPassword').updateValueAndValidity();
      });
  }
  register(): void {
    this.registerService.signUp(this.registerForm.value).subscribe(
      res => {
        this.registerForm.reset();
        this.router.navigate(['login']);
        this.openSnackBar('User registered successfully', 'Success');
      }, error => {
        this.openSnackBar('User not registered', 'Error');
      }
    );
  }
  openSnackBar(message: string, label: string): void {
    this.snackBar.open(message, label, {
      duration: this.durationInSeconds * 1000,
    } );
  }

}
export const confirmPasswordValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {

  if ( !control.parent || !control )
  {
    return null;
  }

  const password = control.parent.get('password');
  const matchingPassword = control.parent.get('matchingPassword');

  if ( !password || !matchingPassword )
  {
    return null;
  }

  if ( matchingPassword.value === '' )
  {
    return null;
  }

  if ( password.value === matchingPassword.value )
  {
    return null;
  }

  return {passwordsNotMatching: true};

}
