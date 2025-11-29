import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AnalyticsService, ChartData } from '../../analytics/analytics.service';
import { RecordService, RecordResponse } from '../../record/record.service';
import { ChartConfiguration, ChartOptions } from 'chart.js';
import { BatchService } from '../batch.service';
import { AuthService } from '../../auth/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-batch-detail',
  templateUrl: './batch-detail.component.html',
  styleUrls: ['./batch-detail.component.scss']
})
export class BatchDetailComponent implements OnInit {
  batchId: number | null = null;
  batch: any = null;
  records: RecordResponse[] = [];
  displayedColumns: string[] = ['date', 'tempMass', 'tempAmbient', 'ph', 'actions'];
  isAdmin = false;

  // Chart Data
  public lineChartData: ChartConfiguration<'line'>['data'] = {
    labels: [],
    datasets: [
      {
        data: [],
        label: 'Loading...',
        fill: true,
        tension: 0.5,
        borderColor: 'black',
        backgroundColor: 'rgba(255,0,0,0.3)'
      }
    ]
  };
  public lineChartOptions: ChartOptions<'line'> = {
    responsive: true
  };
  public lineChartLegend = true;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private analyticsService: AnalyticsService,
    private recordService: RecordService,
    private batchService: BatchService,
    private authService: AuthService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.batchId = Number(this.route.snapshot.paramMap.get('id'));
    this.isAdmin = this.authService.getRole() === 'ADMIN';
    if (this.batchId) {
      this.loadBatch();
      this.loadRecords();
      this.loadTempChart();
    }
  }

  loadBatch() {
    if (this.batchId) {
      this.batchService.getBatchById(this.batchId).subscribe(data => {
        this.batch = data;
      });
    }
  }

  loadRecords() {
    if (this.batchId) {
      this.recordService.getRecordsByBatch(this.batchId).subscribe(data => {
        this.records = data;
      });
    }
  }

  loadTempChart() {
    if (this.batchId) {
      this.analyticsService.getTempMassChart(this.batchId).subscribe(data => {
        this.updateChart(data);
      });
    }
  }

  loadPhChart() {
    if (this.batchId) {
      this.analyticsService.getPhChart(this.batchId).subscribe(data => {
        this.updateChart(data);
      });
    }
  }

  updateChart(data: ChartData) {
    this.lineChartData = {
      labels: data.labels,
      datasets: [
        {
          data: data.data,
          label: data.label,
          fill: true,
          tension: 0.5,
          borderColor: 'blue',
          backgroundColor: 'rgba(0,0,255,0.1)'
        }
      ]
    };
  }

  addRecord() {
    this.router.navigate(['/batches', this.batchId, 'record']);
  }

  onTabChange(event: any) {
    if (event.index === 0) {
      this.loadTempChart();
    } else if (event.index === 1) {
      this.loadPhChart();
    }
  }

  closeBatch() {
    if (!this.batchId) return;

    if (confirm('Are you sure you want to close this batch? This action cannot be undone.')) {
      this.batchService.closeBatch(this.batchId).subscribe({
        next: () => {
          this.snackBar.open('Batch closed successfully', 'Close', { duration: 3000 });
          this.loadBatch(); // Reload to show updated status
        },
        error: () => {
          this.snackBar.open('Error closing batch', 'Close', { duration: 3000 });
        }
      });
    }
  }

  downloadReport() {
    if (!this.batchId) return;

    this.batchService.downloadReport(this.batchId).subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.download = `batch_${this.batchId}_report.xlsx`;
        link.click();
        window.URL.revokeObjectURL(url);
        this.snackBar.open('Report downloaded successfully', 'Close', { duration: 3000 });
      },
      error: () => {
        this.snackBar.open('Error downloading report', 'Close', { duration: 3000 });
      }
    });
  }
}
