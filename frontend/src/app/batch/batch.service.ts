import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Batch {
  id: number;
  code: string;
  cocoaVariety: string;
  grossWeight: number;
  startDate: string;
  endDate?: string;
  status: 'ACTIVE' | 'FINISHED';
  operatorName: string;
  operatorId: number;
}

export interface CreateBatchRequest {
  code: string;
  cocoaVariety: string;
  grossWeight: number;
  startDate: string;
  operatorId: number;
}

export interface UserResponse {
  id: number;
  fullName: string;
  email: string;
}

@Injectable({
  providedIn: 'root'
})
export class BatchService {
  private apiUrl = '/api/batches';
  private usersUrl = '/api/users';

  constructor(private http: HttpClient) { }

  createBatch(batch: CreateBatchRequest): Observable<Batch> {
    return this.http.post<Batch>(this.apiUrl, batch);
  }

  getBatchById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  closeBatch(id: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}/close`, {});
  }

  getFinishedBatches(): Observable<Batch[]> {
    return this.http.get<Batch[]>(`${this.apiUrl}/history`);
  }

  downloadReport(id: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/${id}/report/excel`, {
      responseType: 'blob'
    });
  }

  getBatches(status?: string): Observable<Batch[]> {
    let url = this.apiUrl;
    if (status) {
      url += `?status=${status}`;
    }
    return this.http.get<Batch[]>(url);
  }

  getOperators(): Observable<UserResponse[]> {
    return this.http.get<UserResponse[]>(`${this.usersUrl}/operators`);
  }
}
