import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BatchService } from '../batch.service';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-batch-history',
  templateUrl: './batch-history.component.html',
  styleUrls: ['./batch-history.component.scss']
})
export class BatchHistoryComponent implements OnInit {
  batches: any[] = [];
  displayedColumns: string[] = ['code', 'variety', 'weight', 'startDate', 'endDate', 'operator', 'actions'];
  isAdmin: boolean = false;
  loading: boolean = true;

  constructor(
    private batchService: BatchService,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.isAdmin = this.authService.getRole() === 'ADMIN';
    this.loadFinishedBatches();
  }

  loadFinishedBatches() {
    this.loading = true;
    this.batchService.getFinishedBatches().subscribe({
      next: (data) => {
        this.batches = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error loading finished batches:', err);
        this.loading = false;
      }
    });
  }

  viewBatch(id: number) {
    this.router.navigate(['/batches', id]);
  }

  downloadReport(id: number) {
    this.batchService.downloadReport(id).subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.download = `batch_${id}_report.xlsx`;
        link.click();
        window.URL.revokeObjectURL(url);
      },
      error: (err) => console.error('Error downloading report:', err)
    });
  }
}
