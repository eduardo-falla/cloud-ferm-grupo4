import { Component } from '@angular/core';
import { AuthService } from './auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'frontend';

  constructor(public authService: AuthService, private router: Router) { }

  get isLoggedIn(): boolean {
    return this.authService.isLoggedIn() && !this.isLoginPage;
  }

  get isLoginPage(): boolean {
    return this.router.url === '/login';
  }

  get isAdmin(): boolean {
    return this.authService.isAdmin();
  }

  get userName(): string {
    return this.authService.getCurrentUser()?.fullName || '';
  }

  get userRole(): string {
    const role = this.authService.getRole();
    return role === 'ADMIN' ? 'Administrador' : 'Operario';
  }

  logout() {
    this.authService.logout();
  }
}
