import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RecordService } from '../record.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-record-create',
  templateUrl: './record-create.component.html',
  styleUrls: ['./record-create.component.scss']
})
export class RecordCreateComponent implements OnInit {
  recordForm: FormGroup;
  batchId: number | null = null;
  isLoading = false;

  constructor(
    private fb: FormBuilder,
    private recordService: RecordService,
    private route: ActivatedRoute,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.recordForm = this.fb.group({
      tempMass: ['', [Validators.required, Validators.min(0), Validators.max(100)]],
      tempAmbient: ['', [Validators.required, Validators.min(-10), Validators.max(60)]],
      humidityMass: ['', [Validators.required, Validators.min(0), Validators.max(100)]],
      humidityAmbient: ['', [Validators.required, Validators.min(0), Validators.max(100)]],
      phLevel: ['', [Validators.required, Validators.min(1), Validators.max(14)]],
      isTurned: [false],
      operatorObservations: ['']
    });
  }

  ngOnInit(): void {
    this.batchId = Number(this.route.snapshot.paramMap.get('batchId'));
  }

  onSubmit() {
    if (this.recordForm.valid && this.batchId) {
      this.isLoading = true;
      const request = {
        batchId: this.batchId,
        ...this.recordForm.value
      };

      this.recordService.createRecord(request).subscribe({
        next: () => {
          this.isLoading = false;
          this.snackBar.open('Record saved successfully', 'Close', { duration: 3000 });
          this.router.navigate(['/dashboard']); // Or back to batch details
        },
        error: () => {
          this.isLoading = false;
          this.snackBar.open('Error saving record', 'Close', { duration: 3000, panelClass: ['error-snackbar'] });
        }
      });
    }
  }
}
