package com.example.springintro.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.example.springintro.population.City;

@Component
public class CityRepository {

	private Map<String, City> cities = new HashMap<>();

	@PostConstruct
	public void init() {
		City losAngeles = new City();
		losAngeles.setName("LA");
		losAngeles.setPopulation(5000000);

		cities.put("LA", losAngeles);

		City newYork = new City();
		newYork.setName("NY");
		newYork.setPopulation(6000000);

		cities.put("NY", newYork);

		City chicago = new City();
		chicago.setName("Chicago");
		chicago.setPopulation(4000000);

		cities.put("Chicago", chicago);
	}

	public City getCity(String name) {
		Assert.notNull(name);

		return cities.get(name);
	}
}
