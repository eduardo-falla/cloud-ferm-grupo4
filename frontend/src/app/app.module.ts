import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './auth/login/login.component';
import { AuthInterceptor } from './core/auth.interceptor';

// Material Imports
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { BatchListComponent } from './batch/batch-list/batch-list.component';
import { BatchCreateComponent } from './batch/batch-create/batch-create.component';
import { RecordCreateComponent } from './record/record-create/record-create.component';
import { BatchDetailComponent } from './batch/batch-detail/batch-detail.component';
import { MatTabsModule } from '@angular/material/tabs';
import { NgChartsModule } from 'ng2-charts';
import { UserCreateComponent } from './user/user-create/user-create.component';
import { BatchHistoryComponent } from './batch/batch-history/batch-history.component';
import { AdminCatalogComponent } from './admin/admin-catalog/admin-catalog.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    BatchListComponent,
    BatchCreateComponent,
    RecordCreateComponent,
    BatchDetailComponent,
    UserCreateComponent,
    UserCreateComponent,
    BatchHistoryComponent,
    AdminCatalogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatCardModule,
    MatInputModule,
    MatButtonModule,
    MatFormFieldModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatTableModule,
    MatIconModule,
    MatToolbarModule,
    MatCheckboxModule,
    MatTabsModule,
    NgChartsModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
