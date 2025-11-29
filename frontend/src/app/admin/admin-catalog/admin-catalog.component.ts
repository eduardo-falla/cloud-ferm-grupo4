import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CatalogService, CocoaVariety, Farm } from '../../batch/catalog.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
    selector: 'app-admin-catalog',
    templateUrl: './admin-catalog.component.html',
    styleUrls: ['./admin-catalog.component.scss']
})
export class AdminCatalogComponent implements OnInit {
    varieties: CocoaVariety[] = [];
    farms: Farm[] = [];

    varietyForm: FormGroup;
    farmForm: FormGroup;

    isLoading = false;

    constructor(
        private catalogService: CatalogService,
        private fb: FormBuilder,
        private snackBar: MatSnackBar
    ) {
        this.varietyForm = this.fb.group({
            name: ['', Validators.required],
            description: ['', Validators.required],
            optimalFermentationDays: [6, [Validators.required, Validators.min(1)]]
        });

        this.farmForm = this.fb.group({
            name: ['', Validators.required],
            location: ['', Validators.required],
            ownerName: ['', Validators.required]
        });
    }

    ngOnInit(): void {
        this.loadData();
    }

    loadData() {
        this.catalogService.getVarieties().subscribe(data => this.varieties = data);
        this.catalogService.getFarms().subscribe(data => this.farms = data);
    }

    createVariety() {
        if (this.varietyForm.valid) {
            this.isLoading = true;
            this.catalogService.createVariety(this.varietyForm.value).subscribe({
                next: () => {
                    this.isLoading = false;
                    this.snackBar.open('Variedad creada exitosamente', 'Cerrar', { duration: 3000 });
                    this.varietyForm.reset({ optimalFermentationDays: 6 });
                    this.loadData();
                },
                error: () => {
                    this.isLoading = false;
                    this.snackBar.open('Error al crear variedad', 'Cerrar', { duration: 3000 });
                }
            });
        }
    }

    createFarm() {
        if (this.farmForm.valid) {
            this.isLoading = true;
            this.catalogService.createFarm(this.farmForm.value).subscribe({
                next: () => {
                    this.isLoading = false;
                    this.snackBar.open('Finca creada exitosamente', 'Cerrar', { duration: 3000 });
                    this.farmForm.reset();
                    this.loadData();
                },
                error: () => {
                    this.isLoading = false;
                    this.snackBar.open('Error al crear finca', 'Cerrar', { duration: 3000 });
                }
            });
        }
    }
}
