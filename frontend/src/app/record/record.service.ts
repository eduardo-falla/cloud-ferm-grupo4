import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface CreateRecordRequest {
  batchId: number;
  tempMass: number;
  tempAmbient: number;
  humidityMass: number;
  humidityAmbient: number;
  phLevel: number;
  isTurned: boolean;
  operatorObservations: string;
}

export interface RecordResponse {
  id: number;
  batchId: number;
  recordedAt: string;
  tempMass: number;
  tempAmbient: number;
  humidityMass: number;
  humidityAmbient: number;
  phLevel: number;
  isTurned: boolean;
  operatorObservations: string;
}

@Injectable({
  providedIn: 'root'
})
export class RecordService {
  private apiUrl = '/api/records';

  constructor(private http: HttpClient) { }

  createRecord(record: CreateRecordRequest): Observable<RecordResponse> {
    return this.http.post<RecordResponse>(this.apiUrl, record);
  }

  getRecordsByBatch(batchId: number): Observable<RecordResponse[]> {
    return this.http.get<RecordResponse[]>(`${this.apiUrl}/batch/${batchId}`);
  }
}
