import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../auth/auth.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-user-create',
  templateUrl: './user-create.component.html',
  styleUrls: ['./user-create.component.scss']
})
export class UserCreateComponent {
  userForm: FormGroup;
  isLoading = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.userForm = this.fb.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      role: ['OPERATOR', Validators.required] // Default to OPERATOR
    });
  }

  onSubmit() {
    if (this.userForm.valid) {
      this.isLoading = true;
      this.authService.createUser(this.userForm.value).subscribe({
        next: () => {
          this.isLoading = false;
          this.snackBar.open('User created successfully', 'Close', { duration: 3000 });
          this.router.navigate(['/dashboard']);
        },
        error: () => {
          this.isLoading = false;
          this.snackBar.open('Error creating user', 'Close', { duration: 3000, panelClass: ['error-snackbar'] });
        }
      });
    }
  }
}
