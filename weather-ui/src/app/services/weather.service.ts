import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class WeatherService {

  private apiUrl = 'http://localhost:8080/api/weather';

  constructor(private http: HttpClient) {}

  getWeather(city: string) {
    return this.http.get<any>(`${this.apiUrl}/${city}`);
  }
}
