import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BatchService, UserResponse } from '../batch.service';
import { CatalogService, CocoaVariety, Farm } from '../catalog.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-batch-create',
  templateUrl: './batch-create.component.html',
  styleUrls: ['./batch-create.component.scss']
})
export class BatchCreateComponent implements OnInit {
  batchForm: FormGroup;
  operators: UserResponse[] = [];
  varieties: CocoaVariety[] = [];
  farms: Farm[] = [];
  isLoading = false;

  constructor(
    private fb: FormBuilder,
    private batchService: BatchService,
    private catalogService: CatalogService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.batchForm = this.fb.group({
      code: ['', Validators.required],
      varietyId: ['', Validators.required],
      farmId: [''], // Optional
      grossWeight: ['', [Validators.required, Validators.min(0.1)]],
      startDate: [new Date(), Validators.required],
      operatorId: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadOperators();
    this.loadCatalogs();
  }

  loadOperators() {
    this.batchService.getOperators().subscribe(users => {
      this.operators = users;
    });
  }

  loadCatalogs() {
    this.catalogService.getVarieties().subscribe(data => this.varieties = data);
    this.catalogService.getFarms().subscribe(data => this.farms = data);
  }

  onSubmit() {
    if (this.batchForm.valid) {
      this.isLoading = true;
      this.batchService.createBatch(this.batchForm.value).subscribe({
        next: () => {
          this.isLoading = false;
          this.snackBar.open('Batch created successfully', 'Close', { duration: 3000 });
          this.router.navigate(['/dashboard']);
        },
        error: () => {
          this.isLoading = false;
          this.snackBar.open('Error creating batch', 'Close', { duration: 3000, panelClass: ['error-snackbar'] });
        }
      });
    }
  }
}
