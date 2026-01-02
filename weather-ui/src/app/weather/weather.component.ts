import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { WeatherService } from '../services/weather.service';

@Component({
  selector: 'app-weather',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './weather.component.html',
  styleUrls: ['./weather.component.css']
})
export class WeatherComponent {
  city = '';
  weatherData: any;
  errorMsg = '';
  loading: boolean = false; // ✅ Loading spinner state

  constructor(private weatherService: WeatherService) {}

  searchWeather() {
    if(!this.city.trim()) {
      this.errorMsg = "Please enter a city name!";
      this.weatherData = null;
      return;
    }

    this.loading = true; // ✅ Start spinner
    this.weatherData = null; // hide previous result

    this.weatherService.getWeather(this.city).subscribe({
      next: (data: any) => {
        this.weatherData = data;
        this.errorMsg = '';
        this.loading = false; // ✅ Stop spinner
      },
      error: (err: any) => {
        this.errorMsg = err?.error?.message || 'City not found';
        this.weatherData = null;
        this.loading = false; // ✅ Stop spinner even on error
      }
    });
  }

  // Optional: get weather icon based on description
  getWeatherIcon(desc: string) {
    desc = desc.toLowerCase();
    if(desc.includes('cloud')) return 'assets/cloud.png';
    if(desc.includes('rain')) return 'assets/rain.png';
    if(desc.includes('sun') || desc.includes('clear')) return 'assets/sun.png';
    if(desc.includes('snow')) return 'assets/snow.png';
    return 'assets/weather.png'; // default icon
  }
}
