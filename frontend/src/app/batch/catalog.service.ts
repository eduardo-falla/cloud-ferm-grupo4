import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface CocoaVariety {
    id: number;
    name: string;
    description: string;
    optimalFermentationDays: number;
}

export interface Farm {
    id: number;
    name: string;
    location: string;
    ownerName: string;
}

@Injectable({
    providedIn: 'root'
})
export class CatalogService {
    private apiUrl = 'http://localhost:5000/api/catalog';

    constructor(private http: HttpClient) { }

    getVarieties(): Observable<CocoaVariety[]> {
        return this.http.get<CocoaVariety[]>(`${this.apiUrl}/varieties`);
    }

    getFarms(): Observable<Farm[]> {
        return this.http.get<Farm[]>(`${this.apiUrl}/farms`);
    }

    createVariety(variety: any): Observable<CocoaVariety> {
        return this.http.post<CocoaVariety>(`${this.apiUrl}/varieties`, variety);
    }

    createFarm(farm: any): Observable<Farm> {
        return this.http.post<Farm>(`${this.apiUrl}/farms`, farm);
    }
}
