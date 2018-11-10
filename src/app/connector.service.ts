import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from '../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class ConnectorService {
    private length: number = 10;

    constructor(private http: HttpClient) {

    }

    public getLatestHistoryData(id: number): Observable<any> {
        return this.http.get(`${environment.apiUrl}/coffeeshot?cameraId=${id}&count=${this.length}`);
    }
    public getLatest(): Observable<any> {
        return this.http.get(`${environment.apiUrl}/coffeeshot/latest`);
    }

}
