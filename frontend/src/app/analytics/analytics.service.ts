import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ChartData {
  labels: string[];
  data: number[];
  label: string;
}

@Injectable({
  providedIn: 'root'
})
export class AnalyticsService {
  private apiUrl = '/api/analytics';

  constructor(private http: HttpClient) { }

  getTempMassChart(batchId: number): Observable<ChartData> {
    return this.http.get<ChartData>(`${this.apiUrl}/batch/${batchId}/temp-mass`);
  }

  getPhChart(batchId: number): Observable<ChartData> {
    return this.http.get<ChartData>(`${this.apiUrl}/batch/${batchId}/ph`);
  }
}
