package com.example.jason.rxjava_bestpractice.observerpattern;

import com.example.jason.rxjava_bestpractice.observerpattern.observable.WeatherData;
import com.example.jason.rxjava_bestpractice.observerpattern.observer.CurrentConditionsDisplay;
import com.example.jason.rxjava_bestpractice.observerpattern.observer.ForecastDisplay;

public class WeatherStation {

	public static void main(String[] args) {
		WeatherData weatherData = new WeatherData();
	
		CurrentConditionsDisplay currentDisplay =
			new CurrentConditionsDisplay(weatherData);
		ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData);

		weatherData.setMeasurements(80, 65, 30.4f);
		weatherData.setMeasurements(82, 70, 29.2f);
		weatherData.setMeasurements(78, 90, 29.2f);
	}
}
