import { Component, OnInit } from '@angular/core';
import { Batch, BatchService } from '../batch.service';
import { AuthService } from '../../auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-batch-list',
  templateUrl: './batch-list.component.html',
  styleUrls: ['./batch-list.component.scss']
})
export class BatchListComponent implements OnInit {
  batches: Batch[] = [];
  displayedColumns: string[] = ['code', 'variety', 'startDate', 'status', 'operator', 'actions'];
  isAdmin = false;

  constructor(
    private batchService: BatchService,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.isAdmin = this.authService.getRole() === 'ADMIN';
    this.loadBatches();
  }

  loadBatches() {
    // For now, load all active batches. 
    // In a real app, we might want to filter by operator if not admin, 
    // but the requirements say "Operator sees assigned batches". 
    // The backend endpoint /api/batches returns all if no filter, or we can filter in frontend.
    // Ideally backend should filter based on user context, but for this MVP we'll just show all or filter here.
    // Let's assume the backend returns what's appropriate or we filter.
    // The current backend returns ALL batches. I should probably filter in frontend for Operator.

    this.batchService.getBatches('ACTIVE').subscribe(data => {
      if (this.isAdmin) {
        this.batches = data;
      } else {
        // Filter for current user (Operator)
        // We need the current user ID or name to filter. 
        // The auth response has fullName, let's use that or we need ID.
        // AuthResponse has fullName. Batch has operatorName.
        const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}');
        this.batches = data.filter(b => b.operatorName === currentUser.fullName);
      }
    });
  }

  createBatch() {
    this.router.navigate(['/batches/create']);
  }

  viewDetails(batch: Batch) {
    // Navigate to details (to be implemented)
    this.router.navigate(['/batches', batch.id]);
  }
}
