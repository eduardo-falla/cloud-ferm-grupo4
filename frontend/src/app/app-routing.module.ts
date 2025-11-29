import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { BatchListComponent } from './batch/batch-list/batch-list.component';
import { BatchCreateComponent } from './batch/batch-create/batch-create.component';
import { RecordCreateComponent } from './record/record-create/record-create.component';
import { BatchDetailComponent } from './batch/batch-detail/batch-detail.component';
import { UserCreateComponent } from './user/user-create/user-create.component';
import { BatchHistoryComponent } from './batch/batch-history/batch-history.component';
import { AdminCatalogComponent } from './admin/admin-catalog/admin-catalog.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: BatchListComponent },
  { path: 'batches/create', component: BatchCreateComponent },
  { path: 'batches/history', component: BatchHistoryComponent },
  { path: 'batches/:id', component: BatchDetailComponent },
  { path: 'batches/:batchId/record', component: RecordCreateComponent },
  { path: 'users/create', component: UserCreateComponent },
  { path: 'admin/catalogs', component: AdminCatalogComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
